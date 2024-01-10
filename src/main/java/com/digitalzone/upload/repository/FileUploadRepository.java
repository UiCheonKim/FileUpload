package com.digitalzone.upload.repository;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;

public interface FileUploadRepository {
    void saveFile(Path path, MultipartFile file) throws IOException;
}
