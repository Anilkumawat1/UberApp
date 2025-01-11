package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.SignUpDto;
import com.anilkumawat.project.uberApp.dto.UserDto;
import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Rider;
import com.anilkumawat.project.uberApp.entities.User;
import com.anilkumawat.project.uberApp.entities.enums.Role;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.exceptions.RuntimeConflictException;
import com.anilkumawat.project.uberApp.repositories.UserRepository;
import com.anilkumawat.project.uberApp.services.AuthService;
import com.anilkumawat.project.uberApp.services.DriverService;
import com.anilkumawat.project.uberApp.services.RiderService;
import com.anilkumawat.project.uberApp.services.WalletService;
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
    private final WalletService walletService;
    private final DriverService driverService;
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
}
