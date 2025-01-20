package com.anilkumawat.project.uberApp.services;

import com.anilkumawat.project.uberApp.dto.*;

public interface AuthService {

    String[] login(LoginRequestDto loginRequestDto);

    UserDto signup(SignUpDto signupDto);

    DriverDto onboardNewDriver(Long userId);

    String refreshToken(String refreshToken);
}
