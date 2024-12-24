package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.SignUpDto;
import com.anilkumawat.project.uberApp.dto.UserDto;
import com.anilkumawat.project.uberApp.entities.Rider;
import com.anilkumawat.project.uberApp.entities.User;
import com.anilkumawat.project.uberApp.entities.enums.Role;
import com.anilkumawat.project.uberApp.exceptions.RuntimeConflictException;
import com.anilkumawat.project.uberApp.repositories.UserRepository;
import com.anilkumawat.project.uberApp.services.AuthService;
import com.anilkumawat.project.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeConflictException("User Not found"));

        return "";
    }

    @Override
    @Transactional
    public UserDto signup(SignUpDto signUpDto) {
        User user = userRepository.findByEmail(signUpDto.getEmail()).orElse(null);
        if(user!=null)
            throw new RuntimeConflictException("User Not found");
        user = modelMapper.map(signUpDto,User.class);
        user.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(user);
        riderService.createRider(user);
        //TODO create wallet
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        return null;
    }
}
