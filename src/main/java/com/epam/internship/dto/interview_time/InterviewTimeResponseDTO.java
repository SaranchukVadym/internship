package com.epam.internship.dto.interview_time;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class InterviewTimeResponseDTO {

    @NotNull
    private Long itmId;

    @NotNull
    private Long evId;

    @NotNull
    private Long cnId;

    @NotNull
    private Long empId;

    @NotNull
    private LocalDateTime beginDate;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

}
