package ru.ancap.pkpt.api.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

import java.io.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NetWriter extends Writer {

    @Delegate
    private final Writer delegate;

    public NetWriter(OutputStream stream) {
        this(new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                stream
                        )
                ),
                true
        ) {
            @Override
            public void write(@NotNull String line) {
                super.write(line + "\n");
                super.flush();
            }
        });
    }
}
