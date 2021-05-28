package com.epam.internship.dao;

import com.epam.internship.entity.ResumeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeDAO extends JpaRepository<ResumeEntity, Long> {
    Page<ResumeEntity> findAll(Specification<ResumeEntity> specification, Pageable pageable);
}
