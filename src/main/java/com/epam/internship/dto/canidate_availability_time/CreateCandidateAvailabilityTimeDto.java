package com.epam.internship.dto.canidate_availability_time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateCandidateAvailabilityTimeDto {

    @NotNull(message = "candidateId can't ba null")
    private Long candidateId;

    @NotNull(message = "availabilityTimeSlots can't be null")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private List<LocalDateTime> availableTimeSlots;
}
