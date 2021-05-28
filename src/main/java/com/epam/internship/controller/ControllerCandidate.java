package com.epam.internship.controller;

import com.epam.internship.dto.candidate.CandidateCreateDTO;
import com.epam.internship.dto.candidate.CandidateResponseDTO;
import com.epam.internship.dto.candidate.CandidateUpdateDTO;
import com.epam.internship.dto.canidate_availability_time.CreateCandidateAvailabilityTimeDto;
import com.epam.internship.dto.canidate_availability_time.ResponseCandidateAvailabilityTimeDto;
import com.epam.internship.service.CandidateAvailabilityTimeService;
import com.epam.internship.service.impl.CandidateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class ControllerCandidate {

    private final CandidateServiceImpl candidateService;
    private final CandidateAvailabilityTimeService candidateAvailabilityTimeService;

    @GetMapping(value = "/{id}")
    public CandidateResponseDTO getCandidate(@PathVariable Long id) {
        return candidateService.findById(id);
    }

    @GetMapping
    public Page<CandidateResponseDTO> getAllCandidates(
            @RequestParam(defaultValue = "", name = "search") String search,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "15", name = "itemsPerPage") Integer itemsPerPage) {

        return candidateService.getAllPageable(PageRequest.of(page, itemsPerPage), search);
    }


    @PostMapping
    public CandidateResponseDTO addCandidate(
            @RequestBody CandidateCreateDTO candidateCreateDTO) {

        return candidateService.save(candidateCreateDTO);

    }

    @PutMapping(value = "/{id}")
    public CandidateUpdateDTO updateUpdateCandidate(
            @PathVariable Long id,
            @RequestBody CandidateUpdateDTO candidateUpdateDTO) {
        return candidateService.update(id, candidateUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        candidateService.delete(id);
    }

    @GetMapping("/{candidateId}/availability")
    public ResponseCandidateAvailabilityTimeDto getCandidateAvailabilityTime(@PathVariable Long candidateId) {
        return candidateAvailabilityTimeService.getByCandidateId(candidateId);
    }

    @PostMapping("/availability")
    public ResponseCandidateAvailabilityTimeDto createCandidateAvailabilityTime(
            @RequestBody CreateCandidateAvailabilityTimeDto candidateAvailabilityTimeDto) {
        return candidateAvailabilityTimeService.save(candidateAvailabilityTimeDto);
    }

    @DeleteMapping("/{candidateId}/availability")
    public ResponseEntity<?> deleteCandidateSlots(@PathVariable Long candidateId) {
        candidateAvailabilityTimeService.deleteAllByCandidateId(candidateId);
        return ResponseEntity.ok().build();
    }

}
