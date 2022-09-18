package ru.ancap.pkpt.api.server.task;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.ancap.pkpt.api.messaging.messages.MasterMessage;
import ru.ancap.pkpt.api.messaging.messages.Message;
import ru.ancap.pkpt.api.messaging.messages.MessageCommand;
import ru.ancap.pkpt.api.messaging.workers.RemoteTarget;
import ru.ancap.pkpt.api.task.ServerTask;
import ru.ancap.pkpt.api.util.NetWriter;

import java.io.*;
import java.net.Socket;

@AllArgsConstructor
public class HeaderConsumer implements AccessConsumer {

    private final String secretWord;
    private final ServerTask serverTask;

    @Override
    @SneakyThrows
    public void on(Socket connected, String message) {
        MasterMessage received = MasterMessage.read(message);
        if (!received.getSecretWord().equals(secretWord)) {
            return;
        }
        Writer writer = new NetWriter(connected.getOutputStream());
        RemoteTarget remote = new RemoteTarget() {
            @Override
            public void say(String message) {
                try {
                    writer.write(
                            new MasterMessage(
                                    secretWord,
                                    received.getSession(),
                                    message
                            ).produceNetForm()
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String toString() {
                return connected.getInetAddress().getHostAddress()+":"+connected.getPort();
            }

        };
        this.on(remote, new Message(
                received,
                MessageCommand.parse(received.getMainMessage())
        ));
    }

    private void on(RemoteTarget author, Message message) {
        MessageCommand command = message.getCommand();
        if (command.nextArgument("reply")) {
            command = command.withoutArgument();
            if (command.nextArgument("error")) {
                command = command.withoutArgument();
                if (command.nextArgument("unrecognized")) {
                    throw new RuntimeException("Request to server "+author.toString()+" wasn't understood!");
                }
                throw new RuntimeException("Error was occurred white communicating with server "+author.toString()+" identified as "+command.withoutArgument().nextArgument());
            }
        }
        this.serverTask.on(author, message);
    }
}
