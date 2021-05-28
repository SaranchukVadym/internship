package com.epam.internship.dto.resume;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ResumeResponseDTO {

    private Long id;

    private String link;

    @NotNull
    private String name;

    @NotNull
    private String ext;

    @NotNull
    private Integer size;


    private LocalDateTime createdAt;
}
