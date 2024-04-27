package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.course_conceptualization.MockupId;
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
}
