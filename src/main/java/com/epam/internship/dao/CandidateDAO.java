package com.epam.internship.dao;

import com.epam.internship.entity.CandidateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateDAO extends JpaRepository<CandidateEntity, Long> {
    Page<CandidateEntity> findAll(Specification<CandidateEntity> specification, Pageable pageable);
}