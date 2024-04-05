package edu.kit.elst.course_implementation;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.core.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class StorageService {
    private final Mockups mockups;
    private final UploadedFiles uploadedFiles;

    public MockupId uploadMockup(BuildingBlockVersion buildingBlockVersion, MultipartFile file, String description) {
        UploadedFile uploadedFile = uploadFile(file);

        Mockup mockup = new Mockup(buildingBlockVersion, uploadedFile, UserContext.getUserId());
        mockup.setDescription(description);

        mockups.save(mockup);

        return mockup.id();
    }

    private UploadedFile uploadFile(MultipartFile file) {
        UploadedFile uploadedFile;

        try {
            uploadedFile = new UploadedFile(file.getName(), file.getContentType(), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        uploadedFiles.save(uploadedFile);

        return uploadedFile;
    }

    public Collection<MockupId> getMockups(BuildingBlockVersion buildingBlockVersion) {
        return mockups.findAllMockupsByBuildingBlockVersion(buildingBlockVersion);
    }

    public Optional<UploadedFile> getMockupFile(MockupId mockupId) {
        return uploadedFiles.findByMockupId(mockupId);
    }
}
