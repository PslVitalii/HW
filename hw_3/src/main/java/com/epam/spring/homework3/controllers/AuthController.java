package com.epam.spring.homework3.controllers;

import com.epam.spring.homework3.config.security.jwt.JwtUtils;
import com.epam.spring.homework3.model.dto.AuthenticationRequestDto;
import com.epam.spring.homework3.model.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final UserService userService;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/signup")
	public UserDto signup(@Valid @RequestBody UserDto user) {
		return userService.registerClient(user);
	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@Valid @RequestBody AuthenticationRequestDto request) {
		// Authenticate user. It will throw AuthenticationExc if credentials are invalid
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		// Build jwt token
		String token = JwtUtils.buildToken(authentication);

		// Find user by email
		String username = authentication.getName();
		UserDto userDto = userService.getUser(username);

		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(userDto);
	}
}
