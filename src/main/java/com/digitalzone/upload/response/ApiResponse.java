package com.digitalzone.upload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JeongBoSeong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> res (int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static <T> ApiResponse<T> res (int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    public static <T> ApiResponse<T> res(ApiResponseEnum apiResponseEnum, T data) {
        return ApiResponse.res(apiResponseEnum.getCode(), apiResponseEnum.getMessage(), data);
    }

    public static <T> ApiResponse<T> res(ApiResponseEnum apiResponseEnum) {
        return ApiResponse.res(apiResponseEnum, null);
    }

    public static <T> ApiResponse<T> ok() {
        return ApiResponse.res(ApiResponseEnum.HTTP_OK.getCode(), ApiResponseEnum.HTTP_OK.getMessage(), null);
    }

}
