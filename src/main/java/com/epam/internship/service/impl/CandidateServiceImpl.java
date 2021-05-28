package com.epam.internship.service.impl;

import com.epam.internship.dao.CandidateDAO;
import com.epam.internship.dao.rsql.CustomRsqlVisitor;
import com.epam.internship.dto.candidate.CandidateCreateDTO;
import com.epam.internship.dto.candidate.CandidateResponseDTO;
import com.epam.internship.dto.candidate.CandidateUpdateDTO;
import com.epam.internship.entity.CandidateEntity;
import com.epam.internship.mapper.MapperGeneric;
import com.epam.internship.service.CandidateService;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateDAO candidateDAO;
    private final MapperGeneric mapper;

    @Override
    public CandidateResponseDTO findById(Long id) {
        CandidateEntity candidateEntity = candidateDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));

        return mapper.convertTo(candidateEntity, CandidateResponseDTO.class);
    }

    @Override
    public Page<CandidateResponseDTO> getAllPageable(Pageable pageable, String search) {

        if (search.isEmpty()) {
            return candidateDAO.findAll(pageable).map(candidateEntity -> mapper.convertTo(
                                    candidateEntity, CandidateResponseDTO.class));
        }


        Node rootNode = new RSQLParser().parse(search);
        Specification<CandidateEntity> specification = rootNode.accept(new CustomRsqlVisitor<>());

        return candidateDAO.findAll(specification, pageable).map(candidateEntity -> mapper.convertTo(
                                candidateEntity, CandidateResponseDTO.class));
    }

    @Override
    public CandidateResponseDTO save(CandidateCreateDTO candidateCreateDTO) {

        Map<String, Object> prop = new HashMap<>();
        prop.put("name", candidateCreateDTO.getFirstName());

        return mapper.convertTo(candidateDAO.save(
                mapper.convertTo(candidateCreateDTO, CandidateEntity.class)), CandidateResponseDTO.class);
    }

    @Override
    public CandidateUpdateDTO update(Long id, CandidateUpdateDTO candidateUpdateDTO) {
        CandidateEntity candidateEntity = candidateDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));

        if (candidateUpdateDTO.getRsmId() != null) {
            candidateEntity.setRsmId(candidateUpdateDTO.getRsmId());
        }

        if (candidateUpdateDTO.getFirstName() != null) {
            candidateEntity.setFirstName(candidateUpdateDTO.getFirstName());
        }

        if (candidateUpdateDTO.getLastName() != null) {
            candidateEntity.setLastName(candidateUpdateDTO.getLastName());
        }

        if (candidateUpdateDTO.getPhone() != null) {
            candidateEntity.setPhone(candidateUpdateDTO.getPhone());
        }

        if (candidateUpdateDTO.getSkype() != null) {
            candidateEntity.setSkype(candidateUpdateDTO.getSkype());
        }

        if (candidateUpdateDTO.getEnglishLevel() != null) {
            candidateEntity.setEnglishLevel(candidateUpdateDTO.getEnglishLevel());
        }

        if (candidateUpdateDTO.getMainSkill() != null) {
            candidateEntity.setMainSkill(candidateUpdateDTO.getMainSkill());
        }

        if (candidateUpdateDTO.getOtherSkills() != null) {
            candidateEntity.setOtherSkills(candidateUpdateDTO.getOtherSkills());
        }

        if (candidateUpdateDTO.getEmail() != null) {
            candidateEntity.setEmail(candidateUpdateDTO.getEmail());
        }

        if (candidateUpdateDTO.getInstitution() != null) {
            candidateEntity.setInstitution(candidateUpdateDTO.getInstitution());
        }

        if (candidateUpdateDTO.getFaculty() != null) {
            candidateEntity.setFaculty(candidateUpdateDTO.getFaculty());
        }

        if (candidateUpdateDTO.getSpeciality() != null) {
            candidateEntity.setSpeciality(candidateUpdateDTO.getSpeciality());
        }

        if (candidateUpdateDTO.getGraduationDate() != null) {
            candidateEntity.setGraduationDate(candidateUpdateDTO.getGraduationDate());
        }

        if (candidateUpdateDTO.getCountry() != null) {
            candidateEntity.setCountry(candidateUpdateDTO.getCountry());
        }

        if (candidateUpdateDTO.getCity() != null) {
            candidateEntity.setCity(candidateUpdateDTO.getCity());
        }

        candidateEntity.setUpdatedAt(candidateUpdateDTO.getUpdatedAt());

        return mapper.convertTo(candidateDAO.save(candidateEntity), CandidateUpdateDTO.class);
    }

    @Override
    public void delete(Long id) {
        candidateDAO.deleteById(id);
    }

}
