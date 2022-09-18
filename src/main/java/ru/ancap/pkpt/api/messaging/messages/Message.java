package ru.ancap.pkpt.api.messaging.messages;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Message {

    private final MasterMessage header;
    private final MessageCommand command;

}
