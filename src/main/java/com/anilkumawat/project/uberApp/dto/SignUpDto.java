package com.anilkumawat.project.uberApp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String name;
    private String email;
    private String password;
}
