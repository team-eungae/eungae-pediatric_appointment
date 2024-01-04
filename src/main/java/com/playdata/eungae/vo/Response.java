package com.playdata.eungae.vo;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private HttpStatus httpStatus;
    // Enum으로 바꿔서 정의할 수 있는 방법이 없을까
    private String resultCode;
    private T result;

    public static Response<Void> error(HttpStatus httpStatus,String errorCode){
        return new Response<>(httpStatus ,errorCode,null);
    }

    public static Response<Void> success(HttpStatus httpStatus){
        return new Response<Void>(httpStatus, "success",null);
    }

    public static <T> Response<T> success(HttpStatus httpStatus, T result){
        return new Response<>(httpStatus, "success", result);
    }

}