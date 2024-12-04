package com.anilkumawat.project.uberApp.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ApiResponseDto<T> {
    private LocalDateTime timeStamp;
    private ApiErrorDto apiErrorDto;
    private T data;
    public ApiResponseDto(){
        timeStamp = LocalDateTime.now();
    }
    public ApiResponseDto(ApiErrorDto apiErrorDto){
        this();
        this.apiErrorDto = apiErrorDto;
    }
    public ApiResponseDto(T data){
        this();

        this.data = data;
        System.out.println(this.getData());
    }

}
