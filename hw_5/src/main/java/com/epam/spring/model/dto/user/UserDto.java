package com.epam.spring.model.dto.user;

import com.epam.spring.model.entity.user.Address;
import com.epam.spring.model.entity.user.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    private String firstName;
    private String lastName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

    private Address address;
}
