package edu.kit.elst.content_upload;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.core.UserContext;
import edu.kit.elst.course_implementation.Mockup;
import edu.kit.elst.course_implementation.MockupId;
import edu.kit.elst.course_implementation.Mockups;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class StorageService {
    private final Mockups mockups;
    private final UploadedFiles uploadedFiles;

    public MockupId uploadMockup(BuildingBlockVersion buildingBlockVersion, MultipartFile file, String description) {
        UploadedFile uploadedFile = internalUploadFile(file);

        Mockup mockup = new Mockup(buildingBlockVersion, uploadedFile, UserContext.getUserId());
        mockup.setDescription(description);

        mockups.save(mockup);

        return mockup.id();
    }

    private UploadedFile internalUploadFile(MultipartFile file) {
        UploadedFile uploadedFile;

        try {
            uploadedFile = new UploadedFile(file.getName(), file.getContentType(), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        uploadedFiles.save(uploadedFile);

        return uploadedFile;
    }

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

    public Collection<MockupId> getMockups(BuildingBlockVersion buildingBlockVersion) {
        return mockups.findAllMockupsByBuildingBlockVersion(buildingBlockVersion);
    }

    public Optional<UploadedFile> getMockupFile(MockupId mockupId) {
        return uploadedFiles.findByMockupId(mockupId);
    }

    public void deleteFiles(Set<FileId> fileIds) {
        uploadedFiles.deleteAllById(fileIds);
    }

    public void deleteFile(FileId fileId) {
        uploadedFiles.deleteById(fileId);
    }
}
