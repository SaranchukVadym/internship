package com.epam.internship.dto.candidate;

import com.epam.internship.dto.canidate_availability_time.CreateCandidateAvailabilityTimeDto;
import com.epam.internship.entity.enums.CandidateStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateUpdateDTO {

    private Long id;

    private Long rsmId;

    private List<CreateCandidateAvailabilityTimeDto> availabilityTimeSlots;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String skype;

    private String englishLevel;

    private String mainSkill;

    private String otherSkills;

    private String institution;

    private String faculty;

    private String speciality;

    private LocalDate graduationDate;

    private String country;

    private String city;

    private CandidateStatus status;

    @JsonIgnore
    private final LocalDateTime updatedAt = LocalDateTime.now();
}
