package com.mm.homeworks.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.homeworks.customExceptions.DuplicateEntityException;
import com.mm.homeworks.model.entity.Student;
import com.mm.homeworks.model.request.StudentEditRequest;
import com.mm.homeworks.model.request.StudentUserCreateRequest;
import com.mm.homeworks.model.request.SubjectListIdsDto;
import com.mm.homeworks.model.response.PageableResultDTO;
import com.mm.homeworks.model.response.StudentCreateDTO;
import com.mm.homeworks.model.response.StudentDTO;
import com.mm.homeworks.model.response.StudentEditDTO;
import com.mm.homeworks.service.students.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	private final StudentService studentService;
	private final ModelMapper modelMapper;

	@Autowired
	public StudentController(StudentService studentService, ModelMapper modelMapper) {
		this.studentService = studentService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
//	@Transactional
	public ResponseEntity<?> getAll(Pageable pageable) {
		Page<Student> allStudents = studentService.getAll(pageable);
		
		List<StudentDTO> mappedStudents = allStudents.get().map(s -> {
			StudentDTO response = modelMapper.map(s, StudentDTO.class);
			return response;
		}).collect(Collectors.toList());

		PageableResultDTO<StudentDTO> studentsList = new PageableResultDTO<StudentDTO>(
				allStudents.getTotalElements(), mappedStudents, pageable.getPageNumber(), allStudents.getTotalPages());

		return ResponseEntity.ok(studentsList);
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody StudentUserCreateRequest request) throws DuplicateEntityException, ConstraintViolationException {
		Student student = studentService.create(request); 

		StudentCreateDTO response = modelMapper.map(student, StudentCreateDTO.class);
		response.setUsername(student.getUser().getUsername());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws NoSuchElementException {
		studentService.delete(id); 
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@PathVariable String id, @Valid @RequestBody StudentEditRequest request) throws NoSuchElementException {
		Student editedStudent = studentService.edit(id, request); /// ???
		return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(editedStudent, StudentEditDTO.class));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws NoSuchElementException {
		Student student = studentService.getById(id);
		return ResponseEntity.ok(modelMapper.map(student, StudentDTO.class));
	}
	
	@PutMapping("/{id}/subjects")
	public ResponseEntity<?> addSubjectsListToStudent(@PathVariable(value = "id") String studentId, @RequestBody SubjectListIdsDto subjectIdsList) throws NoSuchElementException{
		studentService.assignSubjectsByIds(studentId, subjectIdsList); //?? dont pass the DTO
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/{id}/subjects")
	public ResponseEntity<?> deleteSubjectsListFromStudent(@PathVariable(value = "id") String studentId, @RequestBody SubjectListIdsDto subjectIdsList) throws NoSuchElementException {
		studentService.deleteSubjectsByIds(studentId, subjectIdsList.getSubjectIds());
		return ResponseEntity.noContent().build();
	}
}
