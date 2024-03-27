package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockDetails;
import edu.kit.elst.building_blocks.BuildingBlockService;
import edut.kit.elst.rest_api.BuildingBlock;
import edut.kit.elst.rest_api.BuildingBlockApi;
import edut.kit.elst.rest_api.BuildingBlockVersion;
import edut.kit.elst.rest_api.RequestBuildingBlockRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

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
}
