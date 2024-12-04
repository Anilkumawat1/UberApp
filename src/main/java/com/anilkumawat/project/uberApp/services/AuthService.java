package com.anilkumawat.project.uberApp.services;

import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.SignUpDto;
import com.anilkumawat.project.uberApp.dto.UserDto;

public interface AuthService {

    String login(String email, String password);

    UserDto signup(SignUpDto signupDto);

    DriverDto onboardNewDriver(Long userId);
}
