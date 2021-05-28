package com.epam.internship.dto.interview_time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InterviewTimeUpdateDTO {

    private Long itmId;

    private Long evId;

    private Long cnId;

    private Long empId;

    private LocalDateTime beginDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
