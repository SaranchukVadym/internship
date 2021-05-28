package com.epam.internship.service;

import com.epam.internship.dto.candidate.CandidateCreateDTO;
import com.epam.internship.dto.candidate.CandidateResponseDTO;
import com.epam.internship.dto.candidate.CandidateUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateService {

    CandidateResponseDTO findById(Long id);

    Page<CandidateResponseDTO> getAllPageable(Pageable pageable, String search);

    CandidateResponseDTO save(CandidateCreateDTO candidateCreateDTO);

    CandidateUpdateDTO update(Long id, CandidateUpdateDTO candidateUpdateDTO);

    void delete(Long id);

}
