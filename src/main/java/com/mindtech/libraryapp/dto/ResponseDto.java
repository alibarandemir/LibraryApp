package com.mindtech.libraryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private Boolean success;
    private String message;
    private T data;

    public static <T> ResponseDto<T> successResponse(T data, String message) {
        return new ResponseDto<>(true, message != null ? message : "Başarılı", data);
    }

    public static <T> ResponseDto<T> errorResponse(String message){
        return new ResponseDto<>(false,message,null);
    }

    
}
