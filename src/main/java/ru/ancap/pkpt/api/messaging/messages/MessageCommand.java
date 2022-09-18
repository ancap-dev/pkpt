package ru.ancap.pkpt.api.messaging.messages;

import lombok.*;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
@With
@EqualsAndHashCode
@ToString
public class MessageCommand {

    private final List<String> argumentList;

    public static MessageCommand parse(String message) {
        return new MessageCommand(
                Arrays.stream(message.split(":")).toList()
        );
    }

    @Nullable
    public String nextArgument() {
        return this.argumentList.size() > 0 ? this.argumentList.get(0) : null;
    }

    public MessageCommand withoutArgument() {
        return this.withArgumentList(
                this.argumentList.stream()
                        .skip(1)
                        .toList()
        );
    }

    public boolean nextArgument(String s) {
        String next = this.nextArgument();
        return next != null && next.equals(s);
    }
}
