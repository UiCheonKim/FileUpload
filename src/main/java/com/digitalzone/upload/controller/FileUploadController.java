package com.digitalzone.upload.controller;

import com.digitalzone.upload.response.ApiResponse;
import com.digitalzone.upload.response.ApiResponseEnum;
import com.digitalzone.upload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private static final Pattern YEAR_PATTERN = Pattern.compile("^\\d{4}$");
    private static final Pattern MONTH_DAY_PATTERN = Pattern.compile("^\\d{2}$");

    @PostMapping("/upload")
    public ApiResponse<Object> saveFile(@RequestParam MultipartFile file,
                                @RequestParam String year,
                                @RequestParam String month,
                                @RequestParam String day,
                                @RequestParam String tenantId) {

        if(!YEAR_PATTERN.matcher(year).matches()) {
            return ApiResponse.res(ApiResponseEnum.INVALID_YEAR);
        }
        if(!MONTH_DAY_PATTERN.matcher(month).matches() || !MONTH_DAY_PATTERN.matcher(day).matches() ) {
            return ApiResponse.res(ApiResponseEnum.INVALID_MONTH_DAY);
        }

        try {
            ConcurrentMap<String, String> res = fileUploadService.uploadFile(file, year, month, day, tenantId);
            return ApiResponse.res(ApiResponseEnum.HTTP_OK, res);
        } catch (IOException e) {
            return ApiResponse.res(ApiResponseEnum.FILE_UPLOAD_FAIL);
        } catch (NullPointerException e) {
            return ApiResponse.res(ApiResponseEnum.INVALID_FILE);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals(ApiResponseEnum.INVALID_FILE.getMessage())) {
                return ApiResponse.res(ApiResponseEnum.INVALID_FILE);
            } else if (e.getMessage().equals(ApiResponseEnum.FILE_SIZE_ZERO.getMessage())) {
                return ApiResponse.res(ApiResponseEnum.FILE_UPLOAD_FAIL);
            } else {
                return ApiResponse.res(ApiResponseEnum.FILE_SIZE_ZERO);
            }
        } catch (Exception e) {
            return ApiResponse.res(ApiResponseEnum.SERVER_ERROR);
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse<Object> handleMissingParams(MissingServletRequestParameterException ex) {
        return ApiResponse.res(ApiResponseEnum.MISSING_REQUEST_PARAMETER.getCode(),ex.getParameterName() + " 누락되었습니다.");
    }
}
