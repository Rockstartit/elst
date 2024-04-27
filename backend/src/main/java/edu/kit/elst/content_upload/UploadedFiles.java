package edu.kit.elst.content_upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UploadedFiles extends JpaRepository<UploadedFile, FileId> {
    static FileId nextIdentity() {
        return new FileId(UUID.randomUUID());
    }
}
