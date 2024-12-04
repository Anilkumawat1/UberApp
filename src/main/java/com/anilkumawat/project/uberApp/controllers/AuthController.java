package com.anilkumawat.project.uberApp.controllers;

import com.anilkumawat.project.uberApp.dto.SignUpDto;
import com.anilkumawat.project.uberApp.dto.UserDto;
import com.anilkumawat.project.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok(authService.signup(signUpDto));
    }
}
