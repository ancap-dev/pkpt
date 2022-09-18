package ru.ancap.pkpt.api.messaging.workers;

import ru.ancap.pkpt.api.task.ClientTask;

public interface RemoteHost {

    void ask(String question, ClientTask task);

}
