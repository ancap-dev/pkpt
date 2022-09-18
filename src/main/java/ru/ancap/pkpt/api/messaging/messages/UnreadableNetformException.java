package ru.ancap.pkpt.api.messaging.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UnreadableNetformException extends Exception {

    private final String explanation;

}
