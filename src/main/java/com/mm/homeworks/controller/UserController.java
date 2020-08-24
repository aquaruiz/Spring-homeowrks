package com.mm.homeworks.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.homeworks.model.entity.User;
import com.mm.homeworks.model.response.PageableResultDTO;
import com.mm.homeworks.model.response.UserDTO;
import com.mm.homeworks.service.users.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public UserController(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(Pageable pageable) {
		Page<User> allUsers = userService.getAll(pageable);
		List<UserDTO> mappedUsers = allUsers.get()
				.map(u -> {
					UserDTO response = modelMapper.map(u, UserDTO.class);
					return response;
				})
				.collect(Collectors.toList());
		
		PageableResultDTO<UserDTO> usersList = new PageableResultDTO<UserDTO>(
																allUsers.getTotalElements(), 
																mappedUsers, 
																pageable.getPageNumber(), 
																allUsers.getTotalPages());
		return ResponseEntity.ok(usersList);
	}
}
