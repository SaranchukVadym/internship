package com.epam.internship.service.impl;

import com.epam.internship.dao.CandidateAvailabilityTimeDAO;
import com.epam.internship.dao.CandidateDAO;
import com.epam.internship.dto.canidate_availability_time.CreateCandidateAvailabilityTimeDto;
import com.epam.internship.dto.canidate_availability_time.ResponseCandidateAvailabilityTimeDto;
import com.epam.internship.dto.canidate_availability_time.TimeId;
import com.epam.internship.entity.CandidateAvailabilityTimeEntity;
import com.epam.internship.entity.CandidateEntity;
import com.epam.internship.service.CandidateAvailabilityTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CandidateAvailabilityTimeServiceImpl implements CandidateAvailabilityTimeService {

    private final CandidateDAO candidateDAO;
    private final CandidateAvailabilityTimeDAO candidateAvailabilityTimeDAO;

    @Override
    public ResponseCandidateAvailabilityTimeDto getByCandidateId(Long id) {
        List<CandidateAvailabilityTimeEntity> availabilityEntities =
                candidateAvailabilityTimeDAO.findAllByCandidateId(id);

        ResponseCandidateAvailabilityTimeDto responseDto = new ResponseCandidateAvailabilityTimeDto();

        if (availabilityEntities == null) {
            return responseDto;
        }

        responseDto.setCandidateId(id);
        availabilityEntities.forEach(dateTime -> responseDto.getAvailabilityTimeSlots().add(new TimeId(dateTime.getId(), dateTime.getDateTime())));

        return responseDto;
    }

    @Override
    public void deleteAllByCandidateId(Long id) {
        candidateAvailabilityTimeDAO.deleteAllByCandidateId(id);
    }

    @Override
    public ResponseCandidateAvailabilityTimeDto save(CreateCandidateAvailabilityTimeDto createDTO) {
        CandidateEntity candidateEntity = candidateDAO.findById(createDTO.getCandidateId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));

        ResponseCandidateAvailabilityTimeDto responseDto = new ResponseCandidateAvailabilityTimeDto();
        responseDto.setCandidateId(createDTO.getCandidateId());

        createDTO.getAvailableTimeSlots().forEach(dateTime -> {
            CandidateAvailabilityTimeEntity availabilityEntity = new CandidateAvailabilityTimeEntity();
            availabilityEntity.setId(null);
            availabilityEntity.setCandidate(candidateEntity);
            availabilityEntity.setDateTime(dateTime);
            availabilityEntity.setCreatedAt(LocalDateTime.now());
            availabilityEntity.setUpdatedAt(LocalDateTime.now());
            Long id = candidateAvailabilityTimeDAO.save(availabilityEntity).getId();
            responseDto.getAvailabilityTimeSlots().add(new TimeId(id, dateTime));
        });

        return responseDto;
    }
}
