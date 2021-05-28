package com.epam.internship.dto.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ResumeCreateDTO {

    @NotNull
    private String link;

    @JsonIgnore
    private final LocalDateTime createdAt = LocalDateTime.now();
}
