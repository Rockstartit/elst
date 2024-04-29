package edu.kit.elst.content_upload;

import edu.kit.elst.core.shared.FileId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class StorageService {
    private final UploadedFiles uploadedFiles;

    public FileId storeFile(MultipartFile file) {
        UploadedFile uploadedFile;

        try {
            uploadedFile = new UploadedFile(file.getName(), file.getContentType(), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        uploadedFiles.save(uploadedFile);

        return uploadedFile.id();
    }

    public void deleteFiles(Set<FileId> fileIds) {
        uploadedFiles.deleteAllById(fileIds);
    }

    public void deleteFile(FileId fileId) {
        uploadedFiles.deleteById(fileId);
    }

    public Optional<UploadedFile> file(FileId fileId) {
        return uploadedFiles.findById(fileId);
    }
}
