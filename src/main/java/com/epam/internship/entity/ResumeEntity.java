package com.epam.internship.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "RESUME")
public class ResumeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RSM_ID", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "resumeEntity")
    private CandidateEntity candidateEntity;

    @Column(name = "RSM_LINK")
    private String link;

    @Column(name = "RSM_NAME")
    private String name;

    @Column(name = "RSM_EXT")
    private String ext;

    @Column(name = "RSM_SIZE")
    private Long size;

    @CreationTimestamp
    @Column(name = "RSM_CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
}
