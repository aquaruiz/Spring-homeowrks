package com.mm.homeworks.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.homeworks.model.entity.Subject;
import com.mm.homeworks.model.entity.Teacher;
import com.mm.homeworks.model.entity.Title;
import com.mm.homeworks.model.response.PageableResultDTO;
import com.mm.homeworks.model.response.SubjectDTO;
import com.mm.homeworks.model.response.SubjectStudentsDTO;
import com.mm.homeworks.service.subjects.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

	private SubjectService subjectService;
	private ModelMapper modelMapper;

	@Autowired
	public SubjectController(SubjectService subjectService, ModelMapper modelMapper) {
		this.subjectService = subjectService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public ResponseEntity<?> getAll(Pageable pageable) {
		Page<Subject> allSubjects = subjectService.getAll(pageable);
		List<SubjectDTO> mappedSubjects = allSubjects.get().map(subject -> {
			SubjectDTO subjectDto = modelMapper.map(subject, SubjectDTO.class);
			return subjectDto;
		}).collect(Collectors.toList());

		PageableResultDTO<SubjectDTO> subjectsList = new PageableResultDTO<SubjectDTO>(
																				allSubjects.getTotalElements(),
																				mappedSubjects,
																				pageable.getPageNumber(),
																				allSubjects.getTotalPages());

		return ResponseEntity.status(HttpStatus.OK).body(subjectsList);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody SubjectDTO request) {
		Subject newSubject = subjectService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(newSubject);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) throws NoSuchElementException {
		Subject subject = subjectService.getById(id);
		SubjectStudentsDTO subjectStudentsDto = modelMapper.map(subject, SubjectStudentsDTO.class);
		subjectStudentsDto.setTeacher(concatTeacherFullnameAndTitle(subject.getTeacher()));
		return ResponseEntity.ok(subjectStudentsDto);
	}

	private String concatTeacherFullnameAndTitle(Teacher teacher) {
		return String.format("%s%s", 
						teacher.getTitle() == Title.NONE ? "" : teacher.getTitle().toString()+" ",
						teacher.getFullname());
	}
}
