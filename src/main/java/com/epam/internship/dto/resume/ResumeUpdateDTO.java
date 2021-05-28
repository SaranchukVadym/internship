package com.epam.internship.dto.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeUpdateDTO {

    private Long id;

    private String link;

    private String name;

    private String ext;

    private Long size;

    @JsonIgnore
    private final LocalDateTime createdAt = LocalDateTime.now();
}
