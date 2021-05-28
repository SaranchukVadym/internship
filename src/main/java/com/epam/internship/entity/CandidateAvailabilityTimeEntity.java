package com.epam.internship.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CANDIDATE_AVAILABILITY_TIME")
public class CandidateAvailabilityTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAT_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "CN_ID", referencedColumnName = "CN_ID")
    private CandidateEntity candidate;

    @Column(name = "CAT_DATETIME")
    private LocalDateTime dateTime;

    @Column(name = "CAT_CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "CAT_UPDATED_AT")
    private LocalDateTime updatedAt;

}
