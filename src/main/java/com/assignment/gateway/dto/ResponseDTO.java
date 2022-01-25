package com.assignment.gateway.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ResponseDTO {
    private Boolean result;
    private Integer status;
    private String message;
    private Object data;

    public static ResponseDTO success(Object data){
        return ResponseDTO.builder()
                .result(true)
                .status(HttpStatus.OK.value())
                .data(data)
                .build();
    }
}
