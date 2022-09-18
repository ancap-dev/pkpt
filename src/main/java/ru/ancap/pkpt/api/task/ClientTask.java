package ru.ancap.pkpt.api.task;

import ru.ancap.pkpt.api.messaging.messages.MessageCommand;

public interface ClientTask {

    void on(MessageCommand reply);
}
