package com.epam.spring.homework3.model.dto;

import com.epam.spring.homework3.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	private Role role;

	private String firstName;
	private String lastName;

	@Email(message = "{email.valid-format}")
	@NotBlank(message = "{email.not-empty}")
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "{password.not-empty}")
	@Size(min = 6, message = "{password.min-length}")
	private String password;

	private boolean enabled;
}
