package com.epam.internship.service.impl;

import com.epam.internship.dao.InterviewTimeDAO;
import com.epam.internship.dao.rsql.CustomRsqlVisitor;
import com.epam.internship.dto.interview_time.InterviewTimeCreateDTO;
import com.epam.internship.dto.interview_time.InterviewTimeResponseDTO;
import com.epam.internship.dto.interview_time.InterviewTimeUpdateDTO;
import com.epam.internship.entity.InterviewTimeEntity;
import com.epam.internship.mapper.MapperGeneric;
import com.epam.internship.service.CandidateService;
import com.epam.internship.service.InterviewTimeService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InterviewTimeServiceImpl implements InterviewTimeService {

    private final InterviewTimeDAO interviewTimeDAO;
    private final CandidateService candidateService;
    private final MapperGeneric mapper;

    @Override
    public InterviewTimeResponseDTO findById(Long id) {
        InterviewTimeEntity interviewTimeEntity = interviewTimeDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The arranged meeting not found"));

        return mapper.convertTo(interviewTimeEntity, InterviewTimeResponseDTO.class);
    }

    @Override
    public Page<InterviewTimeResponseDTO> getAllPageable(Pageable pageable, String search) {

        if (search.isEmpty()) {
            return interviewTimeDAO.findAll(pageable).map(
                    interviewTimeEntity -> mapper.convertTo(interviewTimeEntity, InterviewTimeResponseDTO.class));
        }

        Node rootNode = new RSQLParser().parse(search);
        Specification<InterviewTimeEntity> specification = rootNode.accept(new CustomRsqlVisitor<>());

        return interviewTimeDAO.findAll(specification, pageable).map(
                interviewTimeEntity -> mapper.convertTo(interviewTimeEntity, InterviewTimeResponseDTO.class));
    }

    @Override
    public InterviewTimeCreateDTO save(InterviewTimeCreateDTO interviewTimeCreateDTO) {
        Optional.of(candidateService.findById(interviewTimeCreateDTO.getCnId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));

        return mapper.convertTo(interviewTimeDAO
                        .save(mapper.convertTo(interviewTimeCreateDTO, InterviewTimeEntity.class)),
                InterviewTimeCreateDTO.class);
    }

    @Override
    public InterviewTimeUpdateDTO update(Long id, InterviewTimeUpdateDTO interviewTimeUpdateDTO) {
        InterviewTimeEntity interviewTimeEntity = interviewTimeDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The meeting didn't arrange"));

        if (interviewTimeUpdateDTO.getEvId() != null) {
            interviewTimeEntity.setEvId(interviewTimeUpdateDTO.getEvId());
        }

        if (interviewTimeUpdateDTO.getCnId() != null) {
            interviewTimeEntity.setCnId(interviewTimeUpdateDTO.getCnId());
        }

        if (interviewTimeUpdateDTO.getEmpId() != null) {
            interviewTimeEntity.setEmpId(interviewTimeUpdateDTO.getEmpId());
        }

        if (interviewTimeUpdateDTO.getBeginDate() != null) {
            interviewTimeEntity.setBeginDate(interviewTimeUpdateDTO.getBeginDate());
        }

        interviewTimeEntity.setUpdatedAt(interviewTimeUpdateDTO.getUpdatedAt());

        return mapper.convertTo(interviewTimeDAO
                .save(interviewTimeEntity), InterviewTimeUpdateDTO.class);
    }

    @Override
    public void delete(Long id) {
        interviewTimeDAO.deleteById(id);
    }
}
