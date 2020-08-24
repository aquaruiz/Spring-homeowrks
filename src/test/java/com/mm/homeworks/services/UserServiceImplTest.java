package com.mm.homeworks.services;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mm.homeworks.model.entity.Authority;
import com.mm.homeworks.model.entity.Role;
import com.mm.homeworks.model.entity.User;
import com.mm.homeworks.model.entity.UserType;
import com.mm.homeworks.model.response.UserDTO;
import com.mm.homeworks.repository.RoleRepository;
import com.mm.homeworks.repository.UserRepository;
import com.mm.homeworks.service.users.UserService;
import com.mm.homeworks.service.users.UserServiceImpl;

public class UserServiceImplTest {
//	private static final String USER_ID = "0000-0000-0000";
//    private static final String USERNAME = "mad_hatter";
//    private static final String PASSWORD = "123qwerty";
//    private static final String HASHED_PASSWORD = "$2a$10$f54Op9iL/Onp4bnEqndGpO7anGwYyA/7n2FLxyeOm3OE27eqHf8sS";
//    private static final String ROLE = "ROLE_TEACHER";
//    private static final String ROLE_2 = "ROLE_STUDENT";
//
//    private User user;
//    private Optional<User> optionalUser;
//    private Optional<Role> optionalRole;
//    private Role role;
//
//    UserRepository userRepositoryMock;
//    RoleRepository roleRepositoryMock;
//    UserService userService;
//
//    @BeforeEach
//    public void before() {
//        role = new Role() {{
//            setAuthority(Authority.valueOf(ROLE));
//        }};
//
//        user = new User();
//        user.setUsername(USERNAME);
//        user.setPassword(HASHED_PASSWORD);
//        user.addAuthority(role);
//
//        optionalUser = Optional.of(new User() {{
//            setId(USER_ID);
//            setUsername(USERNAME);
//            setPassword(PASSWORD);
//            addAuthority(role);
//        }});
//
//        this.userRepositoryMock = Mockito.mock(UserRepository.class);
//        this.roleRepositoryMock = Mockito.mock(RoleRepository.class);
//
//
//        userService = new UserServiceImpl(userRepositoryMock);
//	}
//
//    @Test
//    public void register_withNameAndPassword_shouldRegisterNewUser() {
//        UserDTO userDTO = new UserDTO(USERNAME, UserType.STUDENT);
//
//        when(userRepositoryMock.findByUsername(userDTO.getUsername())).thenReturn(null);
//        when(userRepositoryMock.saveAndFlush(any(User.class))).thenReturn(user);
//        when(userRepositoryMock.count()).thenReturn(5l);
//        
//        UserServiceModel savedUser = userService.register(userServiceModel);
//
//        assertEquals(USERNAME, savedUser.getUsername());
//    }
}
