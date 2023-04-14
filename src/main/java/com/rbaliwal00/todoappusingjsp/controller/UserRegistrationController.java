package com.rbaliwal00.todoappusingjsp.controller;

import com.rbaliwal00.todoappusingjsp.dto.AuthenticationRequest;
import com.rbaliwal00.todoappusingjsp.dto.UserDto;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUserAccount(@RequestBody @Valid User user) throws Exception {
        return new ResponseEntity<UserDto>(userService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> authenticateUser(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @GetMapping("/current-user")
    public ResponseEntity<Object> demo()  {
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        }else {
            return  ResponseEntity.ok(null);
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}
