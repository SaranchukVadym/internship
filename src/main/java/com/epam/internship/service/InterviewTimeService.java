package com.epam.internship.service;

import com.epam.internship.dto.interview_time.InterviewTimeCreateDTO;
import com.epam.internship.dto.interview_time.InterviewTimeResponseDTO;
import com.epam.internship.dto.interview_time.InterviewTimeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InterviewTimeService {

    InterviewTimeResponseDTO findById(Long id);

    Page<InterviewTimeResponseDTO> getAllPageable(Pageable pageable, String search);

    InterviewTimeCreateDTO save(InterviewTimeCreateDTO interviewTimeCreateDTO);

    InterviewTimeUpdateDTO update(Long id, InterviewTimeUpdateDTO interviewTimeUpdateDTO);

    void delete(Long id);
}
