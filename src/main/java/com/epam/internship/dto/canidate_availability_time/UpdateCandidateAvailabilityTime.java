package com.epam.internship.dto.canidate_availability_time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class UpdateCandidateAvailabilityTime {

    @NotNull(message = "availabilityTimeSlots can't be null")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private List<LocalDateTime> availabilityTimeSlots;
}
