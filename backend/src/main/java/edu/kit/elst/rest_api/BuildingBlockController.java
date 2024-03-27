package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockDetails;
import edu.kit.elst.building_blocks.BuildingBlockNotFoundException;
import edu.kit.elst.building_blocks.BuildingBlockService;
import edut.kit.elst.rest_api.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        edu.kit.elst.building_blocks.BuildingBlockVersion previousVersion = null;

        if (body.getPreviousVersion() != null) {
            previousVersion = BuildingBlockMapper.mapToBuildingBlockVersion(body.getPreviousVersion());
        }

        BuildingBlockDetails buildingBlockDetails = new BuildingBlockDetails(
                body.getName(), body.getDescription());

        edu.kit.elst.building_blocks.BuildingBlockVersion buildingBlockVersion;
        if (previousVersion != null) {
            buildingBlockVersion = buildingBlockService.registerBuildingBlock(previousVersion, buildingBlockDetails);
        } else {
            buildingBlockVersion = buildingBlockService.registerBuildingBlock(buildingBlockDetails);
        }

        return ResponseEntity.ok(BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockVersion));
    }

    @Override
    public ResponseEntity<BuildingBlock> getBuildingBlock(UUID buildingBlockId, BigDecimal versionNumber) {
        edu.kit.elst.building_blocks.BuildingBlockVersion version
                = new edu.kit.elst.building_blocks.BuildingBlockVersion(buildingBlockId, versionNumber.longValue());

        edu.kit.elst.building_blocks.BuildingBlock buildingBlock = buildingBlockService.buildingBlock(version)
                .orElseThrow(() -> new BuildingBlockNotFoundException(version));

        return ResponseEntity.ok(BuildingBlockMapper.mapToBuildingBlock(buildingBlock));
    }

    @Override
    public ResponseEntity<Void> editBuildingBlock(UUID buildingBlockId, BigDecimal versionNumber, EditBuildingBlockRequest body) {
        edu.kit.elst.building_blocks.BuildingBlockVersion version
                = new edu.kit.elst.building_blocks.BuildingBlockVersion(buildingBlockId, versionNumber.longValue());

        BuildingBlockDetails details = new BuildingBlockDetails(body.getName(), body.getDescription());
        buildingBlockService.editBuildingBlock(version, details);

        return ResponseEntity.ok().build();
    }
}
