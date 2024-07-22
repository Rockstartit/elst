package edu.kit.elst.rest_api;

import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.building_blocks.BuildingBlockMockup;
import edu.kit.elst.building_blocks.BuildingBlockMockupAppService;
import edu.kit.elst.core.shared.MockupId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class BuildingBlockMockupController implements BuildingBlockMockupApi {
    private final BuildingBlockMockupAppService buildingBlockMockupAppService;

    @Override
    public ResponseEntity<List<Mockup>> getAllMockups(UUID buildingBlockId) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);

        Collection<BuildingBlockMockup> mockups = buildingBlockMockupAppService.mockupsByBuildingBlockId(aBuildingBlockId);

        return ResponseEntity.ok(mockups.stream()
                .map(BuildingBlockMapper::mapToMockup)
                .toList());
    }

    @Override
    public ResponseEntity<UUID> uploadMockup(UUID buildingBlockId, String description, MultipartFile file) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);

        MockupId mockupId = buildingBlockMockupAppService.uploadMockup(aBuildingBlockId, file, description);

        return ResponseEntity.ok(mockupId.value());
    }

    @Override
    public ResponseEntity<Void> editMockup(UUID mockupId, EditMockupRequest body) {
        MockupId aMockupId = new MockupId(mockupId);

        buildingBlockMockupAppService.editMockupDescription(aMockupId, body.getDescription());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteMockup(UUID mockupId) {
        MockupId aMockupId = new MockupId(mockupId);

        buildingBlockMockupAppService.deleteMockup(aMockupId);

        return ResponseEntity.ok().build();
    }
}
