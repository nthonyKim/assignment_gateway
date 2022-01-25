package com.assignment.gateway.aop;

import com.assignment.gateway.dto.ResponseDTO;
import com.assignment.gateway.exception.NotFoundException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO notFoundException(Exception e) {
        e.printStackTrace();
        return ResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .result(false)
                .build();
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO feignException(Exception e) {
        e.printStackTrace();
        return ResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .result(false)
                .build();
    }


}
