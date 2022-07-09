package com.epam.spring.service;

import com.epam.spring.exception.EntityAlreadyExistsException;
import com.epam.spring.exception.EntityNotFoundException;
import com.epam.spring.model.dto.user.UserDto;
import com.epam.spring.model.entity.user.Role;
import com.epam.spring.model.entity.user.User;
import com.epam.spring.persistence.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserDto registerClient(UserDto userDto) {
        log.debug("Save client: {}", userDto);
        String email = userDto.getEmail();

        Optional<User> probablyUser = userRepository.findByEmail(email);
        if(probablyUser.isPresent()) {
            log.warn("User with email '{}' already exists", email);
            throw new EntityAlreadyExistsException(email, User.class);
        }

        User user = modelMapper.map(userDto, User.class);
        user.setRole(Role.CLIENT);
        userRepository.save(user);

        return mapUserToUserDto(user);
    }

    public UserDto getUser(long id){
        log.debug("Get user by id: {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, User.class));
        return mapUserToUserDto(user);
    }

    public UserDto getUser(String email){
        log.debug("Get user by email: {}", email);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email, User.class));
        return mapUserToUserDto(user);
    }

    public List<UserDto> getAll(){
        log.debug("Get all users");

        return userRepository.findAll()
                .stream().map(this::mapUserToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapUserToUserDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
