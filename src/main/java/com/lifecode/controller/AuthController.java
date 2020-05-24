package com.lifecode.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lifecode.exception.AppException;
import com.lifecode.jpa.entity.Role;
import com.lifecode.jpa.entity.RoleName;
import com.lifecode.jpa.entity.User;
import com.lifecode.jpa.repository.RoleRepository;
import com.lifecode.jpa.repository.UserRepository;
import com.lifecode.payload.JwtAuthenticationResponse;
import com.lifecode.payload.LoginRequest;
import com.lifecode.payload.Response;
import com.lifecode.payload.SignUpRequest;
import com.lifecode.payload.UserIdentityAvailability;
import com.lifecode.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class AuthController {

	protected Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    
	@PostMapping("/signup")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
        	 if(userRepository.existsByUsername(signUpRequest.getUsername())) {
        		 return ResponseEntity.ok().body(new Response(HttpStatus.BAD_REQUEST,null,"Username is already taken!"));
             }
             if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            	 return ResponseEntity.ok().body(new Response(HttpStatus.BAD_REQUEST, null, "Email Address already in use!"));
             }
             // Creating user's account
             User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                     signUpRequest.getEmail(), signUpRequest.getPassword());
             user.setPassword(passwordEncoder.encode(user.getPassword()));
             
             Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                     .orElseThrow(() -> new AppException("User Role not set."));
             user.setRoles(Collections.singleton(userRole));
             
             User result = userRepository.save(user);
             URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
					.buildAndExpand(result.getUsername()).toUri();
			 
             return ResponseEntity.created(location).body(new Response(HttpStatus.OK, null, "User registered successfully"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
	
    @GetMapping("/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }
}