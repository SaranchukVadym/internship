package com.epam.internship.service;

import com.epam.internship.dto.canidate_availability_time.CreateCandidateAvailabilityTimeDto;
import com.epam.internship.dto.canidate_availability_time.ResponseCandidateAvailabilityTimeDto;

public interface CandidateAvailabilityTimeService {

    ResponseCandidateAvailabilityTimeDto save(CreateCandidateAvailabilityTimeDto createDTO);
    ResponseCandidateAvailabilityTimeDto getByCandidateId(Long id);
    void deleteAllByCandidateId(Long id);
}
