package com.rbaliwal00.todoappusingjsp.service;


import com.rbaliwal00.todoappusingjsp.dto.AuthenticationRequest;
import com.rbaliwal00.todoappusingjsp.dto.UserDto;
import com.rbaliwal00.todoappusingjsp.model.User;

public interface UserService {
    UserDto convertEntityToDto(User user);

    UserDto register(User user) throws Exception;

    UserDto authenticate(AuthenticationRequest request);
}
