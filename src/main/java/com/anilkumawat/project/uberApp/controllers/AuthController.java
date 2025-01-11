package com.anilkumawat.project.uberApp.controllers;

import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.SignUpDto;
import com.anilkumawat.project.uberApp.dto.UserDto;
import com.anilkumawat.project.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        return new ResponseEntity<>(authService.signup(signUpDto), HttpStatus.CREATED);
    }
    @PostMapping(path = "/onBoardNewDriver/{userId}")
    public ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId){
        return new ResponseEntity<>(authService.onboardNewDriver(userId),HttpStatus.CREATED);
    }
}
