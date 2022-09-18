package ru.ancap.pkpt.api.server;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.ancap.pkpt.api.messaging.messages.MasterMessage;
import ru.ancap.pkpt.api.messaging.workers.RemoteHost;
import ru.ancap.pkpt.api.messaging.workers.RemoteTarget;
import ru.ancap.pkpt.api.task.ClientTask;
import ru.ancap.pkpt.api.util.NetWriter;

import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class Client implements RemoteTarget, RemoteHost {

    private final Map<String, ClientTask> taskPool = new ConcurrentHashMap<>();

    private final Socket connection;
    private final String secretWord;

    private boolean enabledReplyListener;

    @Override
    public void ask(String question, ClientTask task) {
        new Thread(() -> {
            String session = UUID.randomUUID().toString();
            this.taskPool.put(session, task);
            if (!this.enabledReplyListener) {
                this.enableReplyListener();
            }
            try {
                Writer writer = new NetWriter(this.connection.getOutputStream());
                String netForm = new MasterMessage(
                        this.secretWord,
                        session,
                        question
                ).produceNetForm();
                writer.write(netForm);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public void say(String message) {
        this.ask(message, reply -> {});
    }

    @SneakyThrows
    private void enableReplyListener() {
        new SocketListener(this.secretWord, (author, message) -> {
            String session = message.getHeader().getSession();
            ClientTask task = this.taskPool.get(session);
            if (task == null) {
                return;
            }
            task.on(message.getCommand());
        }).accept(this.connection);
        this.enabledReplyListener = true;
    }

    public RemoteTarget target() {
        return this;
    }
}
