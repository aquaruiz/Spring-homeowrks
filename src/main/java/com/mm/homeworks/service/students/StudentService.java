package com.mm.homeworks.service.students;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mm.homeworks.customExceptions.DuplicateEntityException;
import com.mm.homeworks.model.entity.Student;
import com.mm.homeworks.model.request.StudentEditRequest;
import com.mm.homeworks.model.request.StudentUserCreateRequest;
import com.mm.homeworks.model.request.SubjectListIdsDto;

public interface StudentService {

	Page<Student> getAll(Pageable pageable);

	Student create(@Valid StudentUserCreateRequest request) throws DuplicateEntityException, ConstraintViolationException;

	void delete(String id) throws NoSuchElementException;

	Student edit(String id, StudentEditRequest student) throws NoSuchElementException, ConstraintViolationException;

	Student getById(String id) throws NoSuchElementException;
	
	void assignSubjectsByIds(String teacherId, SubjectListIdsDto subjectIdsList) throws NoSuchElementException;

	void deleteSubjectsByIds(String studentId, List<Long> subjectIds) throws NoSuchElementException;

}
