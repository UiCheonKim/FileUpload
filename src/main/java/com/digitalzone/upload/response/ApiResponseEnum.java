package com.digitalzone.upload.response;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author JeongBoSeong
 */

@Getter
@AllArgsConstructor
public enum ApiResponseEnum {
    /* HTTP Response */
    HTTP_OK(200, "정상 처리되었습니다."),
    FILE_UPLOAD_FAIL(400, "파일이 비어있습니다."), // 400 Bad Request,
    FILE_SIZE_ZERO(400, "파일의 크기가 0 입니다."), // 400 Bad Request,
    INVALID_YEAR(400, "잘못된 연도 형식입니다."), // 400 Bad Request,
    INVALID_MONTH_DAY(400, "잘못된 월 또는 일 형식입니다."), // 400 Bad Request,
    MISSING_REQUEST_PARAMETER(422, "필수 파라미터가 누락되었습니다."), // 422 Unprocessable Entity
    INVALID_FILE(415, "올바른 파일이 아닙니다."), // 415 Unsupported Media Type
    SERVER_ERROR(500, "알수 없는 서버 에러 입니다."), // 500 Internal Server Error


    EMPTY(0, null);

    private int code;
    private String message;

    public static ApiResponseEnum httpStatusTo(HttpStatus httpStatus) {
        return Arrays.stream(ApiResponseEnum.values())
                .filter(enums -> enums.code == httpStatus.value())
                .findAny()
                .orElse(EMPTY);
    }

}
