package ru.ancap.pkpt.api.task;

import ru.ancap.pkpt.api.messaging.messages.Message;
import ru.ancap.pkpt.api.messaging.workers.RemoteTarget;

public interface ServerTask {

    void on(RemoteTarget author, Message message);
}
