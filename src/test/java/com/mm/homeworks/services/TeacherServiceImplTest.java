package com.mm.homeworks.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mm.homeworks.customExceptions.DuplicateEntityException;
import com.mm.homeworks.model.entity.Authority;
import com.mm.homeworks.model.entity.Role;
import com.mm.homeworks.model.entity.Teacher;
import com.mm.homeworks.model.entity.Title;
import com.mm.homeworks.model.entity.User;
import com.mm.homeworks.model.request.TeacherUserCreateRequest;
import com.mm.homeworks.repository.RoleRepository;
import com.mm.homeworks.repository.SubjectRepository;
import com.mm.homeworks.repository.TeacherRepository;
import com.mm.homeworks.repository.UserRepository;
import com.mm.homeworks.service.teachers.TeacherService;
import com.mm.homeworks.service.teachers.TeacherServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TeacherServiceImplTest {
	@Mock
	UserRepository userRepositoryMock;
	@Mock
	TeacherRepository teacherRepositoryMock;
	@Mock
	SubjectRepository subjectRepositoryMock;
	@Mock
	RoleRepository roleRepositoryMock;
	TeacherService teacherService;

	private static final String USER_ID = "0000-0000-0000";
	private static final String USERNAME = "mad_hatter";
	private static final String PASSWORD = "123qwerty";
	private static final String HASHED_PASSWORD = "$2a$10$f54Op9iL/Onp4bnEqndGpO7anGwYyA/7n2FLxyeOm3OE27eqHf8sS";
	private static final String FULLNAME = "Joan Doe";
	private static final Title TITLE = Title.MISS;
	private static final String ROLE = "ROLE_TEACHER";
	private static final String ROLE_2 = "ROLE_STUDENT";

	private Teacher teacher;
	private User user;
	private Optional<Teacher> optionalTeacher;
	private Optional<Role> optionalRole;
	private Role role;

	@BeforeEach
	public void before() {
		role = new Role();
		role.setAuthority(Authority.TEACHER);

		user = new User();
		user.setUsername(USERNAME);
		user.setPassword(HASHED_PASSWORD);
		user.addAuthority(role);

		teacher = new Teacher();
		teacher.setFullname(FULLNAME);
		teacher.setTitle(TITLE);
		teacher.setUser(user);

		user.setId(USER_ID);
		optionalTeacher = Optional.of(teacher);

		teacherService = new TeacherServiceImpl(teacherRepositoryMock, userRepositoryMock, subjectRepositoryMock,
				new ModelMapper(), new BCryptPasswordEncoder(), roleRepositoryMock);
	}

	@Test
	void shouldCreateNewTeacher() throws ConstraintViolationException, DuplicateEntityException {
		TeacherUserCreateRequest createRequest = new TeacherUserCreateRequest();
		createRequest.setUsername(USERNAME);
		createRequest.setFullname(FULLNAME);
		createRequest.setPassword(PASSWORD);
		createRequest.setConfirmPassword(PASSWORD);
		createRequest.setTitle(TITLE.toString());

		lenient().when(userRepositoryMock.findByUsername(createRequest.getUsername())).thenReturn(new ArrayList<>());
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		lenient().when(roleRepositoryMock.findByAuthorityName(Authority.TEACHER)).thenReturn(roles);
//		lenient().when(teacherRepositoryMock.saveAndFlush(any(Teacher.class))).thenReturn(optionalTeacher.get());
		lenient().when(teacherRepositoryMock.saveAndFlush(teacher)).thenReturn(optionalTeacher.get());

		lenient().when(userRepositoryMock.count()).thenReturn(1l);

		Teacher savedTeacher = teacherService.create(createRequest);

		assertEquals(teacher, savedTeacher, "should return saved teacher");
		assertEquals(1l, userRepositoryMock.count());
	}

}
