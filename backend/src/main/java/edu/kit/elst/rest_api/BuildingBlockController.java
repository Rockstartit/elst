package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockDetails;
import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.building_blocks.BuildingBlockNotFoundException;
import edu.kit.elst.building_blocks.BuildingBlockService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class BuildingBlockController implements BuildingBlockApi {
    private final BuildingBlockService buildingBlockService;

    @Override
    public ResponseEntity<List<BuildingBlock>> getBuildingBlocks() {
        Collection<edu.kit.elst.building_blocks.BuildingBlock> buildingBlocks
                = buildingBlockService.allBuildingBlocks();

        return ResponseEntity.ok(buildingBlocks.stream()
                .map(BuildingBlockMapper::mapToBuildingBlock)
                .toList());
    }

    @Override
    public ResponseEntity<BuildingBlockVersion> requestBuildingBlock(RequestBuildingBlockRequest body) {
        BuildingBlockId previousVersion = null;

        if (body.getPreviousVersion() != null) {
            previousVersion = BuildingBlockMapper.mapToBuildingBlockVersion(body.getPreviousVersion());
        }

        BuildingBlockDetails buildingBlockDetails = new BuildingBlockDetails(
                body.getName(), body.getDescription());

        BuildingBlockId buildingBlockId;
        if (previousVersion != null) {
            buildingBlockId = buildingBlockService.registerBuildingBlock(previousVersion, buildingBlockDetails);
        } else {
            buildingBlockId = buildingBlockService.registerBuildingBlock(buildingBlockDetails);
        }

        return ResponseEntity.ok(BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockId));
    }

    @Override
    public ResponseEntity<BuildingBlock> getBuildingBlock(UUID buildingBlockId, BigDecimal versionNumber) {
        BuildingBlockId version
                = BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockId, versionNumber);

        edu.kit.elst.building_blocks.BuildingBlock buildingBlock = buildingBlockService.buildingBlock(version)
                .orElseThrow(() -> new BuildingBlockNotFoundException(version));

        return ResponseEntity.ok(BuildingBlockMapper.mapToBuildingBlock(buildingBlock));
    }

    @Override
    public ResponseEntity<Void> editBuildingBlock(UUID buildingBlockId, BigDecimal versionNumber, EditBuildingBlockRequest body) {
        BuildingBlockId version
                = BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockId, versionNumber);

        BuildingBlockDetails details = new BuildingBlockDetails(body.getName(), body.getDescription());
        buildingBlockService.editBuildingBlock(version, details);

        return ResponseEntity.ok().build();
    }
}
