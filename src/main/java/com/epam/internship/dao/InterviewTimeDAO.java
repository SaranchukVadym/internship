package com.epam.internship.dao;

import com.epam.internship.entity.InterviewTimeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewTimeDAO extends JpaRepository<InterviewTimeEntity, Long> {
    Page<InterviewTimeEntity> findAll(Specification<InterviewTimeEntity> specification, Pageable pageable);
}
