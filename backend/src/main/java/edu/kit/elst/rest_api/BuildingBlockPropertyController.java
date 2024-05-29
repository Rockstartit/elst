package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.building_blocks.BuildingBlockPropertyAppService;
import edu.kit.elst.building_blocks.BuildingBlockPropertyId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class BuildingBlockPropertyController implements BuildingBlockPropertyApi {
    private final BuildingBlockPropertyAppService propertyAppService;

    @Override
    public ResponseEntity<Void> createBuildingBlockProperty(UUID buildingBlockId, CreateBuildingBlockPropertyRequest body) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);

        propertyAppService.createBuildingBlockProperty(
                aBuildingBlockId,
                body.getKey(),
                body.getDisplayName(),
                body.getDescription(),
                body.getType()
        );

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> editBuildingBlockProperty(UUID buildingBlockId, String key, EditBuildingBlockPropertyRequest body) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);
        BuildingBlockPropertyId propertyId = new BuildingBlockPropertyId(aBuildingBlockId, key);

        propertyAppService.editBuildingBlockProperty(
                propertyId,
                body.getDisplayName(),
                body.getDescription(),
                body.getType()
        );

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteBuildingBlockProperty(UUID buildingBlockId, String key) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);
        BuildingBlockPropertyId propertyId = new BuildingBlockPropertyId(aBuildingBlockId, key);

        propertyAppService.deleteBuildingBlockProperty(propertyId);

        return ResponseEntity.ok().build();
    }
}
