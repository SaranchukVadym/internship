package com.epam.internship.dao;

import com.epam.internship.entity.CandidateAvailabilityTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateAvailabilityTimeDAO extends JpaRepository<CandidateAvailabilityTimeEntity, Long> {
    List<CandidateAvailabilityTimeEntity> findAllByCandidateId(Long candidateId);
    void deleteAllByCandidateId(Long candidateId);
}
