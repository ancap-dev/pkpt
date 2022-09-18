package ru.ancap.pkpt.api.server;

import lombok.AllArgsConstructor;
import ru.ancap.pkpt.api.server.receiver.SocketWorker;
import ru.ancap.pkpt.api.server.task.HeaderConsumer;
import ru.ancap.pkpt.api.task.ServerTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

@AllArgsConstructor
public class Server implements Runnable {

    private final ServerSocket serverSocket;
    private final Consumer<Socket> socketWorker;

    public Server(ServerSocket serverSocket, String secretWord, ServerTask task) {
        this(
                serverSocket,
                new SocketWorker(
                        new HeaderConsumer(
                                secretWord,
                                task
                        )
                )
        );
    }

    @Override
    public void run() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket connected = serverSocket.accept();
                    socketWorker.accept(connected);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
