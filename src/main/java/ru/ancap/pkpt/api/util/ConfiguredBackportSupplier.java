package ru.ancap.pkpt.api.util;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

import java.util.function.Supplier;

@AllArgsConstructor
public class ConfiguredBackportSupplier implements Supplier<Integer> {

    @Delegate
    private final Supplier<Integer> backPortSupplier;

    public static Supplier<Integer> of(boolean useDirectBackPort, int directBackPort) {
        if (!useDirectBackPort) {
            return new FreePort();
        }
        return () -> directBackPort;
    }
}
