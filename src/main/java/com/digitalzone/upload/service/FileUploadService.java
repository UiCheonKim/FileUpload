package com.digitalzone.upload.service;

import com.digitalzone.upload.repository.FileUploadRepository;
import com.digitalzone.upload.response.ApiResponseEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    @Value("${file.upload.path}")
    private String fileDir;
    private final FileUploadRepository fileUploadRepository;

    public ConcurrentMap<String, String> uploadFile(MultipartFile file,
                                                        String year,
                                                        String month,
                                                        String day,
                                                        String tenantId) throws IOException {
        ConcurrentHashMap<String, String> res = new ConcurrentHashMap<>();
        String fullPathStr = "";
        Path path;

        if(file.isEmpty()) {
            throw new IllegalArgumentException(ApiResponseEnum.FILE_UPLOAD_FAIL.getMessage());
        }

        if(file.getSize() == 0) {
            throw new IllegalArgumentException(ApiResponseEnum.FILE_SIZE_ZERO.getMessage());
        }

        if ("application/octet-stream".equals(file.getContentType())) {
            throw new IllegalArgumentException(ApiResponseEnum.INVALID_FILE.getMessage());
        }

        path = Paths.get(fileDir, year, month, day, tenantId, file.getOriginalFilename());
        Files.createDirectories(path.getParent());
        fileUploadRepository.saveFile(path, file);
        fullPathStr = path.toString().replace("\\", "/");

        try {
            res.put("fullPath", fullPathStr);
            res.put("fileName", Objects.requireNonNull(file.getOriginalFilename()));
            res.put("fileType", Objects.requireNonNull(file.getContentType()));
            res.put("fileSizeBytes", String.valueOf(file.getSize()));
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(ApiResponseEnum.INVALID_FILE.getMessage());
        }

        return res;
    }
}
