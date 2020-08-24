package com.mm.homeworks.service.users;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mm.homeworks.model.entity.User;
import com.mm.homeworks.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Page<User> getAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
		try {
			UserDetails user = userRepository.findByUsername(username).get(0);
			user.getAuthorities().size();
			return user;
		} catch (Exception e) {
			throw new BadCredentialsException("Incorrect username or password");
		}
	}
}
