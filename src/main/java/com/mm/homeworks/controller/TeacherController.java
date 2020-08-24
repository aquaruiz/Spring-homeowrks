package com.mm.homeworks.controller;

import java.util.Arrays;
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
import com.mm.homeworks.model.entity.Subject;
import com.mm.homeworks.model.entity.Teacher;
import com.mm.homeworks.model.request.SubjectListIdsDto;
import com.mm.homeworks.model.request.TeacherEditRequest;
import com.mm.homeworks.model.request.TeacherUserCreateRequest;
import com.mm.homeworks.model.response.PageableResultDTO;
import com.mm.homeworks.model.response.SubjectDTO;
import com.mm.homeworks.model.response.TeacherDTO;
import com.mm.homeworks.model.response.TeacherEditDTO;
import com.mm.homeworks.model.response.TeacherCreateDTO;
import com.mm.homeworks.service.subjects.SubjectService;
import com.mm.homeworks.service.teachers.TeacherService;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
	private final TeacherService teacherService;
	private final SubjectService subjectService;
	private final ModelMapper modelMapper;

	@Autowired
	public TeacherController(TeacherService teacherService, SubjectService subjectService, ModelMapper modelMapper) {
		this.teacherService = teacherService;
		this.subjectService = subjectService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public ResponseEntity<?> getAll(Pageable pageable) {
		Page<Teacher> allTeachers = teacherService.getAll(pageable);

		List<TeacherDTO> mappedTeachers = allTeachers.get().map(t -> {
			TeacherDTO response = modelMapper.map(t, TeacherDTO.class);
			SubjectDTO[] subjects = modelMapper.map(t.getSubjects(), SubjectDTO[].class);
			Arrays.stream(subjects).forEach(response::addSubject);
			return response;
		}).collect(Collectors.toList());

		PageableResultDTO<TeacherDTO> teachersList = new PageableResultDTO<TeacherDTO>(allTeachers.getTotalElements(),
				mappedTeachers, pageable.getPageNumber(), allTeachers.getTotalPages());

		return ResponseEntity.ok(teachersList);
	}

	@PreAuthorize("isAnonymous()")
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody TeacherUserCreateRequest request)
			throws DuplicateEntityException {
		Teacher teacher = teacherService.create(request);
		TeacherCreateDTO response = modelMapper.map(teacher, TeacherCreateDTO.class);
		response.setUsername(teacher.getUser().getUsername());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws NoSuchElementException {
		teacherService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@PathVariable String id, @Valid @RequestBody TeacherEditRequest request)
			throws ConstraintViolationException, NoSuchElementException {
		Teacher editedTeacher = teacherService.edit(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(editedTeacher, TeacherEditDTO.class));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws NoSuchElementException {
		Teacher teacher = teacherService.getById(id);
		return ResponseEntity.ok(modelMapper.map(teacher, TeacherDTO.class));
	}

	@GetMapping("/{id}/subjects")
	public ResponseEntity<?> getAllTeacherSubjects(@PathVariable String id) throws NoSuchElementException {
		List<Subject> teacherSubjects = subjectService.getAllByTeacherId(id);
		List<SubjectDTO> teacherSubjectsDtos = Arrays.asList(modelMapper.map(teacherSubjects, SubjectDTO[].class));
		return ResponseEntity.ok(teacherSubjectsDtos);
	}

	@PostMapping("/{id}/subjects")
	public ResponseEntity<?> addNewSubjectToTeacher(@PathVariable String id, @Valid @RequestBody SubjectDTO request) {
		Teacher teacher = teacherService.addNewSubjectToTeacher(id, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(teacher, TeacherDTO.class));
	}

	@PutMapping("/{id}/subjects")
	public ResponseEntity<?> assignExistingSubjectsToTeacher(@PathVariable(value = "id") String teacherId,
			@RequestBody SubjectListIdsDto subjectIdsList) {
		Teacher teacher = teacherService.assignExistingSubjects(teacherId, subjectIdsList.getSubjectIds());
		return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(teacher, TeacherDTO.class));
	}

	@DeleteMapping("/{id}/subjects/{subjectId}")
	public ResponseEntity<?> removeSubjectFromTeacher(@PathVariable(value = "id") String teacherId,
			@RequestBody SubjectListIdsDto subjectIdsList) {
		teacherService.removeExistingSubjects(teacherId, subjectIdsList.getSubjectIds());
		return ResponseEntity.noContent().build();
	}
}
