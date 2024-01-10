package com.digitalzone.upload.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;

@Repository
public class FileUploadRepositoryImpl implements FileUploadRepository{
    @Override
    public void saveFile(Path path, MultipartFile file) throws IOException {
        file.transferTo(path.toFile());
    }
}
