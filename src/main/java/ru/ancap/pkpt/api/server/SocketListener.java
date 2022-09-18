package ru.ancap.pkpt.api.server;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import ru.ancap.pkpt.api.server.receiver.SocketWorker;
import ru.ancap.pkpt.api.server.task.HeaderConsumer;
import ru.ancap.pkpt.api.task.ServerTask;

import java.net.Socket;
import java.util.function.Consumer;

@AllArgsConstructor
public class SocketListener implements Consumer<Socket> {

    @Delegate
    private final Consumer<Socket> delegate;

    public SocketListener(String secretWord, ServerTask task) {
        this(
                new SocketWorker(
                        new HeaderConsumer(
                                secretWord,
                                task
                        )
                )
        );
    }
}
