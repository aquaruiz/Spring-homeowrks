package com.mm.homeworks.service.subjects;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mm.homeworks.constants.ErrorMessages;
import com.mm.homeworks.model.entity.Subject;
import com.mm.homeworks.model.response.SubjectDTO;
import com.mm.homeworks.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	private SubjectRepository subjectRepository;
	private ModelMapper modelMapper;

	@Autowired
	public SubjectServiceImpl(SubjectRepository subjectRepository, ModelMapper modelMapper) {
		this.subjectRepository = subjectRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Page<Subject> getAll(Pageable pageable) {
		return subjectRepository.findAll(pageable);
	}

	@Override
	public List<Subject> getAllByTeacherId(String id) throws NoSuchElementException {
		List<Subject> teacherSubjectsList = subjectRepository.getAllSubjectsByTeacherId(id);
		return teacherSubjectsList;
	}

	@Override
	public Subject create(SubjectDTO subjectDto) {
		Subject newSubject = modelMapper.map(subjectDto, Subject.class);
		return subjectRepository.saveAndFlush(newSubject);
	}

	@Override
	@Transactional
	public Subject getById(Long id) throws NoSuchElementException {
		Optional<Subject> subjectOptional = subjectRepository.findById(id);
		if(subjectOptional.isEmpty()) {
			throw new NoSuchElementException(ErrorMessages.getEntityWithIdNotExists("Subject", String.valueOf(id)));
		}
		
		Subject subject = subjectOptional.get();
		subject.getStudents().size();
		subject.getTeacher().getFullname();
		return subject;
	}
}
