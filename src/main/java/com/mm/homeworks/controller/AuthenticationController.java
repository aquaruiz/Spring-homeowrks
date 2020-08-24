package com.mm.homeworks.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.homeworks.authentication.JwtTokenProvider;
import com.mm.homeworks.model.request.LoginRequest;
import com.mm.homeworks.model.response.JwtAuthenticationDTO;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

	private final JwtTokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;

	public AuthenticationController(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping()
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest model) throws Exception {
		Authentication authentication;

		authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationDTO(jwt));
	}
}
