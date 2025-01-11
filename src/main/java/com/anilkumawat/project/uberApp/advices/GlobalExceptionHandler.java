package com.anilkumawat.project.uberApp.advices;


import com.anilkumawat.project.uberApp.dto.ApiErrorDto;
import com.anilkumawat.project.uberApp.dto.ApiResponseDto;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.exceptions.RuntimeConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> ResourceNotFoundException(Exception e){
        ApiErrorDto apiErrorDto = ApiErrorDto
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return buildErrorResponse(apiErrorDto);
    }

    @ExceptionHandler(RuntimeConflictException.class)
    public ResponseEntity<ApiResponseDto<?>> InternalServerError(RuntimeConflictException e){
        ApiErrorDto apiErrorDto = ApiErrorDto
                .builder()
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .build();
        return buildErrorResponse(apiErrorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<?>> handleInternalServerError(Exception exception) {
        ApiErrorDto apiError = ApiErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<?>> MethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> error = e
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorDto apiErrorDto = ApiErrorDto
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .subError(error)
                .message("Input validation failed")
                .build();


        return buildErrorResponse(apiErrorDto);

    }

    public ResponseEntity<ApiResponseDto<?>> buildErrorResponse(ApiErrorDto apiErrorDto){
        return new ResponseEntity<>(new ApiResponseDto<>(apiErrorDto), apiErrorDto.getStatus());
    }
}
