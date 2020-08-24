package com.mm.homeworks.service.teachers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

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
import com.mm.homeworks.model.entity.Subject;
import com.mm.homeworks.model.entity.Teacher;
import com.mm.homeworks.model.entity.Title;
import com.mm.homeworks.model.entity.User;
import com.mm.homeworks.model.entity.UserType;
import com.mm.homeworks.model.request.TeacherEditRequest;
import com.mm.homeworks.model.request.TeacherUserCreateRequest;
import com.mm.homeworks.model.response.SubjectDTO;
import com.mm.homeworks.repository.RoleRepository;
import com.mm.homeworks.repository.SubjectRepository;
import com.mm.homeworks.repository.TeacherRepository;
import com.mm.homeworks.repository.UserRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository teacherRepository;
	private final UserRepository userRepository;
	private final SubjectRepository subjectRepository;
	private final RoleRepository roleRepository;
	private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
	
	@Autowired
	public TeacherServiceImpl(TeacherRepository teacherRepository, UserRepository userRepository, SubjectRepository subjectRepository, ModelMapper modelMapper, PasswordEncoder encoder, RoleRepository roleRepository
) {
		this.teacherRepository = teacherRepository;
		this.userRepository = userRepository;
		this.subjectRepository = subjectRepository;
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
	    this.encoder = encoder;
	}
	
	@Override
	@Transactional
	public Page<Teacher> getAll(Pageable pageable) {
		Page<Teacher> teachersList = teacherRepository.findAll(pageable);
		return teachersList;
	}

	@Override
	public Teacher create(TeacherUserCreateRequest request) throws DuplicateEntityException, ConstraintViolationException {
		List<User> users = userRepository.findByUsername(request.getUsername());
		if (users.size() > 0) {
			throw new DuplicateEntityException(ErrorMessages.getUsernameAlreadyTaken(request.getUsername()));
		}
		
		User newUser = modelMapper.map(request, User.class);
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		newUser.setUserType(UserType.TEACHER);
		
		Role teacherRole = roleRepository.findByAuthorityName(Authority.TEACHER).get(0);
		newUser.addAuthority(teacherRole);
		
		Teacher newTeacher = modelMapper.map(request, Teacher.class);
		newTeacher.setUser(newUser);
		Title title = convertStringtoTitleEnum(request.getTitle());
		newTeacher.setTitle(title);
		
		Teacher teacher = teacherRepository.saveAndFlush(newTeacher);
		return teacher;
	}

	@Override
	public void delete(String id) {
		Teacher teacher = this.getById(id);
		Set<Subject> subjects = teacher.getSubjects();
		subjects.forEach(Subject::removeTeacher);
		subjectRepository.saveAll(subjects);
		
		teacherRepository.delete(teacher);
	}

	@Override
	public Teacher edit(String id, TeacherEditRequest request) throws ConstraintViolationException, NoSuchElementException {
		Teacher teacher = this.getById(id);	
		teacher.setFullname(request.getFullname());
		teacher.setTitle(convertStringtoTitleEnum(request.getTitle()));
		teacher = teacherRepository.saveAndFlush(teacher);
		
		return teacher;
	}

	private Title convertStringtoTitleEnum(String string) {
		try {
			String titleString = string.toUpperCase().replace(".", "").trim();
			return Title.valueOf(titleString);
		} catch (NullPointerException | IllegalArgumentException e) {
			return Title.NONE;
		}
	}

	@Override
	@Transactional
	public Teacher addNewSubjectToTeacher(String id, SubjectDTO request) throws NoSuchElementException {
		Teacher teacher = this.getById(id);
		
		Subject newSubject = modelMapper.map(request, Subject.class);
		newSubject.setTeacher(teacher);
		teacher.addSubject(newSubject);
		subjectRepository.saveAndFlush(newSubject);
		return teacher;
	}

	@Override
	public Teacher getById(String id) throws NoSuchElementException {
		Optional<Teacher> teacherOptional = teacherRepository.findById(id);
		if(teacherOptional.isEmpty()) {
			throw new NoSuchElementException(ErrorMessages.getEntityWithIdNotExists("Teacher", id));
		}
		
		return teacherOptional.get();
	}

	@Override
	@Transactional
	public Teacher assignExistingSubjects(String teacherId, List<Long> subjectIds) throws NoSuchElementException {
		Teacher teacher = this.getById(teacherId);
		List<Subject> subjects = new ArrayList<Subject>();
		
		subjectIds.stream().forEach(subjectId -> {
			Subject subject = subjectRepository.findById(subjectId).orElseThrow(NoSuchElementException::new);
			subjects.add(subject);
		});
		
		subjects.forEach(subject -> {
			subject.setTeacher(teacher);
			teacher.addSubject(subject);
		});
		subjectRepository.saveAll(subjects);
		teacherRepository.saveAndFlush(teacher);
		teacher.getSubjects().size();
		return teacher;
	}
	
	@Override
	@Transactional
	public Teacher removeExistingSubjects(String teacherId, List<Long> subjectIds) throws NoSuchElementException {
		Teacher teacher = this.getById(teacherId);
		List<Subject> subjects = new ArrayList<Subject>();
		
		subjectIds.stream().forEach(subjectId -> {
			Subject subject = subjectRepository.findById(subjectId).orElseThrow(NoSuchElementException::new);
			if (subject.getTeacher().equals(teacher)) {
				subjects.add(subject);
			}
		});
		
		subjects.forEach(subject -> subject.removeTeacher());
		subjectRepository.saveAll(subjects);
		teacherRepository.saveAndFlush(teacher);
		teacher.getSubjects();
		return teacher;
	}
}
