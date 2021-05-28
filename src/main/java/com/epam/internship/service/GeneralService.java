package com.epam.internship.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GeneralService<R, C, U> {
    R getById(Long id);
    List<R> getAll();
    Page<R> getAllPageable(Pageable pageable, String search);
    R save(C createEmployeeDTO);
    R update(Long id, U updateEmployeeDto);
    void delete(Long id);
}
