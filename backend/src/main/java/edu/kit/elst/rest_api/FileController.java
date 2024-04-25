package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.course_implementation.MockupId;
import edu.kit.elst.course_implementation.ReadMeAppService;
import edu.kit.elst.content_upload.StorageService;
import edu.kit.elst.content_upload.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RestController
@AllArgsConstructor
public class FileController implements FileApi {
    private final StorageService storageService;
    private final ReadMeAppService readMeAppService;

    @Override
    public ResponseEntity<Void> editBuildingBlockReadMe(UUID buildingBlockId, BigDecimal versionNumber, EditFileContentRequest body) {
        BuildingBlockVersion buildingBlockVersion = BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockId, versionNumber);

        readMeAppService.editReadMe(buildingBlockVersion, body.getContent());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> getBuildingBlockReadMe(UUID buildingBlockId, BigDecimal versionNumber) {
        BuildingBlockVersion buildingBlockVersion = BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockId, versionNumber);

        String content = readMeAppService.getReadMe(buildingBlockVersion);

        return ResponseEntity.ok(content);
    }

    @Override
    public ResponseEntity<List<UUID>> uploadMockup(UUID buildingBlockId, BigDecimal versionNumber, List<MultipartFile> files) {
        BuildingBlockVersion buildingBlockVersion = BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockId, versionNumber);

        Collection<MockupId> mockupIds = new ArrayList<>();

        for (MultipartFile file : files) {
            MockupId mockupId = storageService.uploadMockup(buildingBlockVersion, file, "UI Mockup");
            mockupIds.add(mockupId);
        }

        return ResponseEntity.ok(mockupIds.stream()
                .map(MockupId::value)
                .toList());
    }

    @Override
    public ResponseEntity<byte[]> downloadMockup(UUID mockupId) {
        MockupId aMockupId = new MockupId(mockupId);

        UploadedFile uploadedFile = storageService.getMockupFile(aMockupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uploadedFile.name() + "\"")
                .body(uploadedFile.data());
    }

    @Override
    public ResponseEntity<List<UUID>> getBuildingBlockMockups(UUID buildingBlockId, BigDecimal versionNumber) {
        BuildingBlockVersion buildingBlockVersion = BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockId, versionNumber);

        Collection<MockupId> mockupIds = storageService.getMockups(buildingBlockVersion);

        return ResponseEntity.ok(mockupIds.stream()
                .map(MockupId::value)
                .toList());
    }
}
