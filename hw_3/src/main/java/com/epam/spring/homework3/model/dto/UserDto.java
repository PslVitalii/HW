package com.epam.spring.homework3.model.dto;

import com.epam.spring.homework3.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return enabled == userDto.enabled && role == userDto.role && Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, email, enabled);
    }
}
