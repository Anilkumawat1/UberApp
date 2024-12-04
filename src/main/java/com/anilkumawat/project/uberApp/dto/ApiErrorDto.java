package com.anilkumawat.project.uberApp.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
@Data
@Builder
public class ApiErrorDto {

    private HttpStatus status;
    private String message;
    private List<String> subError;

}
