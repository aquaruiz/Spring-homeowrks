package com.mm.homeworks.service.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mm.homeworks.model.entity.User;


public interface UserService extends UserDetailsService {

	Page<User> getAll(Pageable pageable);

	UserDetails loadUserByUsername(String username);

}
