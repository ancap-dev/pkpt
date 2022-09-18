package ru.ancap.pkpt.api.messaging.messages;

import java.net.Socket;

public interface ServerAccess {

    Socket connected();
    String message();

}
