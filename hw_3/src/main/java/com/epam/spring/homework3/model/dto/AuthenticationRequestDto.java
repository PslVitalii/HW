package com.epam.spring.homework3.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequestDto {

	@Email(message = "{email.valid-format}")
	@NotBlank(message = "{email.not-empty}")
	private String email;

	@NotBlank(message = "{password.not-empty}")
	private String password;
}
