package com.epam.spring.homework3.controllers;

import com.epam.spring.homework3.config.security.jwt.JwtUtils;
import com.epam.spring.homework3.model.dto.AuthenticationRequestDto;
import com.epam.spring.homework3.model.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static utils.Utils.objectToJson;

@WebMvcTest(
        controllers = AuthController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class),
        excludeAutoConfiguration = {UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class}
)
@ActiveProfiles("test")
class AuthControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private MockMvc mvc;

    private final String AUTH_API = "/api/auth";

    @Test
    void testSignup() throws Exception {
        // given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@mail.com");
        userDto.setPassword("password");

        // when
        when(userService.registerClient(userDto)).thenReturn(userDto);

        // then
        mvc.perform(
                post(AUTH_API + "/signup")
                    .content(objectToJson(userDto))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(userDto.getEmail())));

        verify(userService).registerClient(userDto);
    }

    @Test
    void testSignupReturnsBadRequestForInvalidArguments() throws Exception {
        // given
        UserDto userDto = new UserDto();
        userDto.setEmail("test-mail.com");
        userDto.setPassword("pass");

        // when
        when(userService.registerClient(userDto)).thenReturn(userDto);

        // then
        mvc.perform(
                post(AUTH_API + "/signup")
                    .content(objectToJson(userDto))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation error")))
                .andExpect(jsonPath("$.subErrors", hasSize(2)));
    }

    @Test
    void testLogin() throws Exception {
        // given
        String email = "test@mail.com";

        UserDto userDto = new UserDto();
        userDto.setEmail(email);

        AuthenticationRequestDto authRequest = new AuthenticationRequestDto();
        authRequest.setEmail(email);
        authRequest.setPassword("password");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
        String jwt = "jwt";

        // when
        MockedStatic<JwtUtils> jwtUtils = Mockito.mockStatic(JwtUtils.class);
        jwtUtils.when(() -> JwtUtils.buildToken(authenticationToken)).thenReturn(jwt);

        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);
        when(userService.getUser(email)).thenReturn(userDto);

        // then
        mvc.perform(
                post(AUTH_API + "/login")
                    .content(objectToJson(authRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, jwt))
                .andExpect(jsonPath("$.email", is(email)));

        verify(authenticationManager).authenticate(authenticationToken);
        verify(userService).getUser(email);
    }


    @Test
    void testLoginReturnsBadRequestForInvalidArguments() throws Exception {
        // given
        AuthenticationRequestDto authRequest = new AuthenticationRequestDto();
        authRequest.setEmail("email");
        authRequest.setPassword(null);

        // then
        mvc.perform(
                post(AUTH_API + "/login")
                    .content(objectToJson(authRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors", hasSize(2)));
    }
}
