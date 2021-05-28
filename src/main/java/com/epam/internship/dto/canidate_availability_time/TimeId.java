package com.epam.internship.dto.canidate_availability_time;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class TimeId {
    @NotNull
    private final Long slotId;

    @NotNull
    private final LocalDateTime dateTime;
}
