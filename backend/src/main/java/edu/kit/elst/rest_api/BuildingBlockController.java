package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.*;
import edu.kit.elst.core.shared.BuildingBlockId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class BuildingBlockController implements BuildingBlockApi {
    private final BuildingBlockAppService buildingBlockService;

    @Override
    public ResponseEntity<List<BuildingBlock>> getBuildingBlocks() {
        Collection<edu.kit.elst.building_blocks.BuildingBlock> buildingBlocks
                = buildingBlockService.allBuildingBlocks();

        return ResponseEntity.ok(buildingBlocks.stream()
                .map(BuildingBlockMapper::mapToBuildingBlock)
                .toList());
    }

    @Override
    public ResponseEntity<UUID> requestBuildingBlock(RequestBuildingBlockRequest body) {
        BuildingBlockDetails buildingBlockDetails = new BuildingBlockDetails(
                body.getName(), body.getDescription());
        Technology technology = new Technology(body.getTechnology());

        BuildingBlockId buildingBlockId = buildingBlockService.registerBuildingBlock(buildingBlockDetails, technology);

        return ResponseEntity.ok(buildingBlockId.value());
    }

    @Override
    public ResponseEntity<BuildingBlock> getBuildingBlock(UUID buildingBlockId) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);

        edu.kit.elst.building_blocks.BuildingBlock buildingBlock = buildingBlockService.buildingBlock(aBuildingBlockId)
                .orElseThrow(() -> new BuildingBlockNotFoundException(aBuildingBlockId));

        return ResponseEntity.ok(BuildingBlockMapper.mapToBuildingBlock(buildingBlock));
    }

    @Override
    public ResponseEntity<Void> editBuildingBlock(UUID buildingBlockId, EditBuildingBlockRequest body) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);

        BuildingBlockDetails details = new BuildingBlockDetails(body.getName(), body.getDescription());
        Technology technology = new Technology(body.getTechnology());
        buildingBlockService.editBuildingBlock(aBuildingBlockId, details, technology);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> releaseBuildingBlock(UUID buildingBlockId) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);

        buildingBlockService.releaseBuildingBlock(aBuildingBlockId);

        return ResponseEntity.ok().build();
    }
}
