package com.epam.internship.controller;


import com.epam.internship.dto.interview_time.InterviewTimeCreateDTO;
import com.epam.internship.dto.interview_time.InterviewTimeResponseDTO;
import com.epam.internship.dto.interview_time.InterviewTimeUpdateDTO;
import com.epam.internship.service.impl.InterviewTimeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interviewtime")
@RequiredArgsConstructor
public class ControllerInterviewTime {

    private final InterviewTimeServiceImpl interviewTimeService;

    @GetMapping(value = "/{id}")
    public InterviewTimeResponseDTO getInterviewTime(@PathVariable Long id) {
        return interviewTimeService.findById(id);
    }

    @GetMapping
    public Page<InterviewTimeResponseDTO> getAllInterviewTime(
            @RequestParam(defaultValue = "", name = "search") String search,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "15", name = "itemsPerPage") Integer itemsPerPage) {
        return interviewTimeService.getAllPageable(PageRequest.of(page, itemsPerPage), search);
    }


    @PostMapping
    public InterviewTimeCreateDTO addInterviewTime(
            @RequestBody InterviewTimeCreateDTO interviewTimeCreateDTO) {
        return interviewTimeService.save(interviewTimeCreateDTO);
    }

    @PutMapping(value = "/{id}")
    public InterviewTimeUpdateDTO updateInterviewTime(
            @PathVariable Long id,
            @RequestBody InterviewTimeUpdateDTO interviewTimeUpdateDTO) {
        return interviewTimeService.update(id, interviewTimeUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        interviewTimeService.delete(id);
    }
}
