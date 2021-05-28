package com.epam.internship.service;

import com.epam.internship.dto.resume.ResumeResponseDTO;
import com.epam.internship.dto.resume.ResumeUpdateDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {

    ResumeResponseDTO getById(Long id);

    List<ResumeResponseDTO> getAllPageable(Pageable pageable, String search);

    ResumeResponseDTO save(Long candidateId, MultipartFile file, String link);

    ResumeUpdateDTO update(Long id, ResumeUpdateDTO resumeUpdateDTO);

    void delete(Long id);
}
