package com.epam.internship.dto.canidate_availability_time;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseCandidateAvailabilityTimeDto {

    @NotNull
    private Long candidateId;

    @NotNull
    private List<TimeId> availabilityTimeSlots = new ArrayList<>();
}
