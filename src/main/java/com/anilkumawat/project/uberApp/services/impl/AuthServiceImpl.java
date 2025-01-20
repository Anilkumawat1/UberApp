package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.dto.*;
import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.User;
import com.anilkumawat.project.uberApp.entities.enums.Role;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.exceptions.RuntimeConflictException;
import com.anilkumawat.project.uberApp.repositories.UserRepository;
import com.anilkumawat.project.uberApp.security.JwtService;
import com.anilkumawat.project.uberApp.services.AuthService;
import com.anilkumawat.project.uberApp.services.DriverService;
import com.anilkumawat.project.uberApp.services.RiderService;
import com.anilkumawat.project.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public String[] login(LoginRequestDto loginRequestDto) {
        String[] tokens = new String[2];
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        User user = (User)authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokens[0] = accessToken;
        tokens[1] = refreshToken;

        return tokens;
    }

    @Override
    @Transactional
    public UserDto signup(SignUpDto signUpDto) {
        User user = userRepository.findByEmail(signUpDto.getEmail()).orElse(null);
        if(user!=null)
            throw new RuntimeConflictException("Account is already exists");
        user = modelMapper.map(signUpDto,User.class);
        user.setRoles(Set.of(Role.RIDER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        riderService.createRider(savedUser);
        //TODO create wallet
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    @Transactional
    public DriverDto onboardNewDriver(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with id: "+userId));
        if(user.getRoles().contains(Role.DRIVER)){
            throw new RuntimeConflictException("User with id "+user.getId()+ "is already driver");
        }
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        Driver driver = driverService.createNewDriver(user);
        return modelMapper.map(driver,DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " +
                "with id: "+userId));

        return jwtService.generateAccessToken(user);
    }
}
