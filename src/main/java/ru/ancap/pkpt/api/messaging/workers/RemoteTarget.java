package ru.ancap.pkpt.api.messaging.workers;

public interface RemoteTarget {

    void say(String message);

    default void replyUnrecognized() {
        this.say("reply:error:unrecognized");
    }

}
