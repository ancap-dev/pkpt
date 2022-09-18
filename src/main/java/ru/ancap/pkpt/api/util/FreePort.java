package ru.ancap.pkpt.api.util;

import lombok.SneakyThrows;

import java.net.ServerSocket;
import java.util.function.Supplier;

public class FreePort implements Supplier<Integer> {

    @Override
    @SneakyThrows
    public Integer get() {
        int port = 0;
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
        }
        if (port > 0) {
            return port;
        }
        throw new RuntimeException("Could not find a free port");
    }
}
