package com.epam.internship.service;

import com.epam.internship.dto.candidate.CandidateResponseDTO;
import com.epam.internship.dto.file.UploadFileResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileUploadService {

    UploadFileResponseDTO uploadFile(CandidateResponseDTO candidateResponseDTO, MultipartFile file);

    Resource loadAsResource(String filename);

    Path load(String filename);

}
