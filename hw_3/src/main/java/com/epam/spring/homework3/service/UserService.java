package com.epam.spring.homework3.service;

import com.epam.spring.homework3.exceptions.EntityAlreadyExistsException;
import com.epam.spring.homework3.exceptions.EntityNotFoundException;
import com.epam.spring.homework3.model.dto.UserDto;
import com.epam.spring.homework3.model.entity.Role;
import com.epam.spring.homework3.model.entity.User;
import com.epam.spring.homework3.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserDto registerClient(UserDto userDto){
        String email = userDto.getEmail();

        Optional<User> possibleUser = userRepository.findByEmail(email);
        if(possibleUser.isPresent()){
            throw new EntityAlreadyExistsException(email, User.class);
        }

        User user = modelMapper.map(userDto, User.class);
        System.out.println(user);
        user.setRole(Role.CLIENT);
        userRepository.save(user);

        return mapUserToUserDto(user);
    }

    public void deleteUser(long id){
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, User.class));
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(long id){
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, User.class));
        return mapUserToUserDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(email, User.class));
        return mapUserToUserDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(){
        return userRepository.findAll()
                .stream().map(this::mapUserToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto mapUserToUserDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
