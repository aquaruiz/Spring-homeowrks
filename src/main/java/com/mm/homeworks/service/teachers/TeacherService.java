package com.mm.homeworks.service.teachers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mm.homeworks.customExceptions.DuplicateEntityException;
import com.mm.homeworks.model.entity.Teacher;
import com.mm.homeworks.model.request.TeacherEditRequest;
import com.mm.homeworks.model.request.TeacherUserCreateRequest;
import com.mm.homeworks.model.response.SubjectDTO;

public interface TeacherService {

	Page<Teacher> getAll(Pageable pageable);
	
	Teacher create(@Valid TeacherUserCreateRequest request) throws DuplicateEntityException, ConstraintViolationException;

	void delete(String id) throws NoSuchElementException;

	Teacher edit(String id, TeacherEditRequest teacher) throws NoSuchElementException, ConstraintViolationException;

	Teacher addNewSubjectToTeacher(String id, @Valid SubjectDTO request) throws NoSuchElementException;

	Teacher getById(String id) throws NoSuchElementException;

	Teacher assignExistingSubjects(String teacherId, List<Long> subjectIds) throws NoSuchElementException;
	
	Teacher removeExistingSubjects(String teacherId, List<Long> subjectIds) throws NoSuchElementException;

}
