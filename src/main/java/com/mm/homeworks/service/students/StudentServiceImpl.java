package com.mm.homeworks.service.students;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mm.homeworks.constants.ErrorMessages;
import com.mm.homeworks.customExceptions.DuplicateEntityException;
import com.mm.homeworks.model.entity.Authority;
import com.mm.homeworks.model.entity.Role;
import com.mm.homeworks.model.entity.Student;
import com.mm.homeworks.model.entity.Subject;
import com.mm.homeworks.model.entity.User;
import com.mm.homeworks.model.entity.UserType;
import com.mm.homeworks.model.request.StudentEditRequest;
import com.mm.homeworks.model.request.StudentUserCreateRequest;
import com.mm.homeworks.model.request.SubjectListIdsDto;
import com.mm.homeworks.repository.RoleRepository;
import com.mm.homeworks.repository.StudentRepository;
import com.mm.homeworks.repository.SubjectRepository;
import com.mm.homeworks.repository.UserRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final SubjectRepository subjectRepository;
	private final RoleRepository roleRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder encoder;
	
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository, SubjectRepository subjectRepository, ModelMapper modelMapper, PasswordEncoder encoder, RoleRepository roleRepository) {
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
		this.subjectRepository = subjectRepository;
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
		this.encoder = encoder;
	}

	@Override
	@Transactional
	public Page<Student> getAll(Pageable pageable) {
		return studentRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Student create(StudentUserCreateRequest request) throws DuplicateEntityException, ConstraintViolationException {
		List<User> users = userRepository.findByUsername(request.getUsername());
		if (users.size() > 0) {
			throw new DuplicateEntityException(ErrorMessages.getUsernameAlreadyTaken(request.getUsername()));
		}
		
		User newUser = modelMapper.map(request, User.class);
		newUser.setUserType(UserType.STUDENT);
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		
		Role studentRole = roleRepository.findByAuthorityName(Authority.STUDENT).get(0);
		newUser.addAuthority(studentRole);

		Student newStudent = modelMapper.map(request, Student.class);
		newStudent.setUser(newUser);
		
		Student student = studentRepository.saveAndFlush(newStudent);
		return student;
	}

	@Override
	public void delete(String id) throws NoSuchElementException {
		Student student = this.getById(id);
		studentRepository.delete(student);
	}

	@Override
	public Student edit(String id, StudentEditRequest request) throws NoSuchElementException, ConstraintViolationException {
		Student student = this.getById(id);

		student.setFirstName(request.getFirstName());
		student.setLastName(request.getLastName());
		student.setAge(request.getAge());
		
		student = studentRepository.save(student);
		return student;
	}

	@Override
	@Transactional
	public Student getById(String id) throws NoSuchElementException {
		Optional<Student> studentOptional = studentRepository.findById(id);
		if(studentOptional.isEmpty()) {
			throw new NoSuchElementException(ErrorMessages.getEntityWithIdNotExists("Student", id));
		}
		
		return studentOptional.get();
	}		
	
	@Override
	@Transactional
	public void assignSubjectsByIds(String studentId, SubjectListIdsDto subjectIdsList) throws NoSuchElementException {
		Student student = this.getById(studentId);
		List<Subject> subjects = new ArrayList<Subject>();
		
		subjectIdsList.getSubjectIds().stream().forEach(subjectId -> {
			Subject subject = subjectRepository.findById(subjectId).orElseThrow(NoSuchElementException::new);
			subjects.add(subject);
		});
		
		subjects.forEach(subject -> subject.addStudent(student));
		studentRepository.saveAndFlush(student);
	}

	@Override
	@Transactional
	public void deleteSubjectsByIds(String studentId, List<Long> subjectIds) throws NoSuchElementException {
		Student student = this.getById(studentId);
		List<Subject> subjects = new ArrayList<Subject>();
		
		subjectIds.stream().forEach(subjectId -> {
			Subject subject = subjectRepository.findById(subjectId).orElseThrow(NoSuchElementException::new);
			subjects.add(subject);
		});
		
		subjects.forEach(subject -> subject.removeStudent(student));
		studentRepository.saveAndFlush(student);		
	}
}
