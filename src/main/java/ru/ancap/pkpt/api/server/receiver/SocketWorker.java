package ru.ancap.pkpt.api.server.receiver;

import lombok.AllArgsConstructor;
import ru.ancap.pkpt.api.server.task.AccessConsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

@AllArgsConstructor
public class SocketWorker implements Consumer<Socket> {

    private final AccessConsumer consumer;

    @Override
    public void accept(Socket connected) {
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connected.getInputStream()));
                String message = reader.readLine();
                if (message == null) {
                    throw new NullPointerException("Normally working server can't send null line!");
                }
                consumer.on(connected, message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
