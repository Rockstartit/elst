package edu.kit.elst.course_implementation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UploadedFiles extends JpaRepository<UploadedFile, FileId> {
    static FileId nextIdentity() {
        return new FileId(UUID.randomUUID());
    }

    @Query("select file from Mockup mockup " +
            "join mockup.file file " +
            "where mockup.id = :mockupId")
    Optional<UploadedFile> findByMockupId(MockupId mockupId);
}
