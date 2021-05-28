package com.epam.internship.controller;

import com.epam.internship.dto.resume.ResumeResponseDTO;
import com.epam.internship.dto.resume.ResumeUpdateDTO;
import com.epam.internship.service.impl.ResumeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ControllerResume {

    private final ResumeServiceImpl resumeService;

    @GetMapping(value = "/{id}")
    public ResumeResponseDTO getResume(@PathVariable Long id) {
        return resumeService.getById(id);
    }

    @GetMapping
    public List<ResumeResponseDTO> getAll(
            @RequestParam(defaultValue = "", value = "search") String search,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "9", name = "itemsPerPage") Integer itemsPerPage) {

        return resumeService.getAllPageable(PageRequest.of(page, itemsPerPage), search);
    }

    @PostMapping
    public ResumeResponseDTO addResume(
            @RequestParam("id") Long candidateId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "link", required = false) String link) {
        return resumeService.save(candidateId, file, link);
    }

    @PutMapping(value = "/{id}")
    public ResumeUpdateDTO updateResume(
            @PathVariable Long id,
            @RequestBody ResumeUpdateDTO resumeUpdateDTO) {
        return resumeService.update(id, resumeUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteResume(@PathVariable Long id) {
        resumeService.delete(id);
    }
}
