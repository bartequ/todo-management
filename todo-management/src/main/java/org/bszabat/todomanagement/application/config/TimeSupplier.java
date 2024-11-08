package org.bszabat.todomanagement.application.config;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeSupplier {

    public LocalDateTime nowAsLocalDateTime() {
        return LocalDateTime.now();
    }
}
