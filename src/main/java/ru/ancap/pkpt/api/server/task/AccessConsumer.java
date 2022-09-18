package ru.ancap.pkpt.api.server.task;

import java.net.Socket;

public interface AccessConsumer {

    void on(Socket connected, String message);

}
