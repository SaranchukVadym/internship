package com.epam.internship.service.impl;

import com.epam.internship.dao.CandidateDAO;
import com.epam.internship.dao.ResumeDAO;
import com.epam.internship.dao.rsql.CustomRsqlVisitor;
import com.epam.internship.dto.candidate.CandidateResponseDTO;
import com.epam.internship.dto.candidate.CandidateUpdateDTO;
import com.epam.internship.dto.file.UploadFileResponseDTO;
import com.epam.internship.dto.resume.ResumeCreateDTO;
import com.epam.internship.dto.resume.ResumeResponseDTO;
import com.epam.internship.dto.resume.ResumeUpdateDTO;
import com.epam.internship.entity.CandidateEntity;
import com.epam.internship.entity.ResumeEntity;
import com.epam.internship.mapper.MapperGeneric;
import com.epam.internship.service.ResumeService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private final FileUploadServiceImpl fileUploadService;
    private final CandidateServiceImpl candidateService;
    private final ResumeDAO resumeDAO;
    private final MapperGeneric mapper;
    private final CandidateDAO candidateDAO;

    @Override
    public ResumeResponseDTO getById(Long id) {
        ResumeEntity resumeEntity = resumeDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The resume not found"));

        return mapper.convertTo(resumeEntity, ResumeResponseDTO.class);
    }

    @Override
    public Page<ResumeResponseDTO> getAllPageable(Pageable pageable, String search) {

        if (search.isEmpty()) {
            return resumeDAO.findAll(pageable).map(
                    resumeEntity -> mapper.convertTo(resumeEntity, ResumeResponseDTO.class));
        }

        Node rootNode = new RSQLParser().parse(search);
        Specification<ResumeEntity> specification = rootNode.accept(new CustomRsqlVisitor<>());

        return resumeDAO.findAll(specification, pageable).map(
                resumeEntity -> mapper.convertTo(resumeEntity, ResumeResponseDTO.class));
    }

    @Override
    public ResumeResponseDTO save(Long candidateId,
                                     MultipartFile file,
                                     String link) {

        CandidateEntity candidateEntity = candidateDAO.findById(candidateId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));

        CandidateResponseDTO candidateResponseDTO =  mapper.convertTo(candidateEntity, CandidateResponseDTO.class);

        if (file != null) {

            UploadFileResponseDTO uploadFileResponseDTO =
                    fileUploadService.uploadFile(candidateResponseDTO, file);

            ResumeEntity resumeEntity = resumeDAO.save(ResumeEntity.builder()
                    .name(uploadFileResponseDTO.getFileName())
                    .ext(uploadFileResponseDTO.getFileExtension())
                    .size(uploadFileResponseDTO.getSize())
                    .createdAt(LocalDateTime.now())
                    .build());

            candidateService.update(
                    candidateResponseDTO.getId(),
                    CandidateUpdateDTO.builder().rsmId(resumeEntity.getId()).build());

            candidateResponseDTO.setRsmId(resumeEntity.getId());

            return mapper.convertTo(resumeEntity, ResumeResponseDTO.class);
        }

        return mapper.convertTo(
                resumeDAO.save(mapper.convertTo(new ResumeCreateDTO(link), ResumeEntity.class)),
                        ResumeResponseDTO.class);
    }

    @Override
    public ResumeUpdateDTO update(Long id, ResumeUpdateDTO resumeUpdateDTO) {
        ResumeEntity resumeEntity = resumeDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found"));

        if (resumeUpdateDTO.getLink() != null) {
            resumeEntity.setLink(resumeUpdateDTO.getLink());
        }

        if (resumeUpdateDTO.getName() != null) {
            resumeEntity.setName(resumeUpdateDTO.getName());
        }

        if (resumeUpdateDTO.getExt() != null) {
            resumeEntity.setExt(resumeUpdateDTO.getExt());
        }

        if (resumeUpdateDTO.getSize() != null) {
            resumeEntity.setSize(resumeUpdateDTO.getSize());
        }

        return mapper.convertTo(
                resumeDAO.save(mapper.convertTo(resumeUpdateDTO, ResumeEntity.class)),
                ResumeUpdateDTO.class);
    }

    @Override
    public void delete(Long id) {
        resumeDAO.deleteById(id);
    }
}