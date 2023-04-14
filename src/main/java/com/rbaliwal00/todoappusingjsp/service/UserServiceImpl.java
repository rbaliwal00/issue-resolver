package com.rbaliwal00.todoappusingjsp.service;


import com.rbaliwal00.todoappusingjsp.dto.AuthenticationRequest;
import com.rbaliwal00.todoappusingjsp.dto.UserDto;
import com.rbaliwal00.todoappusingjsp.model.Role;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper,
                           JWTService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto convertEntityToDto(User user) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        UserDto userDto = new UserDto();
        userDto = modelMapper.map(user,UserDto.class);
        return userDto;
    }

    @Override
    public UserDto register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DataIntegrityViolationException("Username already exists!");
        }
        var userInstance = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(userInstance);
        var jwtToken = jwtService.generateToken(user);
        UserDto userDto = convertEntityToDto(user);
        userDto.setId(userInstance.getId());
        userDto.setToken(jwtToken);
        return userDto;
    }

    @Override
    public UserDto authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        UserDto userDto = convertEntityToDto(user);
        userDto.setId(user.getId());
        userDto.setToken(jwtToken);
        return userDto;
    }


}
