package com.epam.internship.entity;

import com.epam.internship.entity.enums.CandidateStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "CANDIDATE")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CN_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "CN_ID", insertable = false, updatable = false)
    private InterviewTimeEntity interviewTimeEntity;

    @Column(name = "RSM_ID")
    private Long rsmId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RSM_ID", insertable = false, updatable = false)
    private ResumeEntity resumeEntity;

    @Column(name = "CN_FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "CN_LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "CN_PHONE", nullable = false)
    private String phone;

    @Column(name = "CN_EMAIL", nullable = false)
    private String email;

    @Column(name = "CN_SKYPE")
    private String skype;

    @Column(name = "CN_ENGLISH_LEVEL", nullable = false)
    private String englishLevel;

    @Column(name = "CN_MAIN_SKILL")
    private String mainSkill;

    @Column(name = "CN_OTHER_SKILLS")
    private String otherSkills;

    @Column(name = "CN_INSTITUTION")
    private String institution;

    @Column(name = "CN_FACULTY")
    private String faculty;

    @Column(name = "CN_SPECIALITY")
    private String speciality;

    @Column(name = "CN_GRADUATION_DATE")
    private LocalDate graduationDate;

    @Column(name = "CN_COUNTRY")
    private String country;

    @Column(name = "CN_CITY")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "CN_STATUS")
    private CandidateStatus status;

    @CreationTimestamp
    @Column(name = "CN_CREATED_AT")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "CN_UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;
}
