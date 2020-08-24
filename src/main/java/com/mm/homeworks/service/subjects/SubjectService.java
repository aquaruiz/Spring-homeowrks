package com.mm.homeworks.service.subjects;


import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mm.homeworks.model.entity.Subject;
import com.mm.homeworks.model.response.SubjectDTO;

public interface SubjectService {

	Page<Subject> getAll(Pageable pageable);

	List<Subject> getAllByTeacherId(String id) throws NoSuchElementException;

	Subject create(SubjectDTO request);

	Subject getById(Long id) throws NoSuchElementException;

}
