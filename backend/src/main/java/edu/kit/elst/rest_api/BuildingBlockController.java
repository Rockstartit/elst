package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockDetails;
import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.building_blocks.BuildingBlockNotFoundException;
import edu.kit.elst.building_blocks.BuildingBlockService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UUID> requestBuildingBlock(RequestBuildingBlockRequest body) {
        BuildingBlockDetails buildingBlockDetails = new BuildingBlockDetails(
                body.getName(), body.getDescription());

        BuildingBlockId buildingBlockId = buildingBlockService.registerBuildingBlock(buildingBlockDetails);

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
        buildingBlockService.editBuildingBlock(aBuildingBlockId, details);

        return ResponseEntity.ok().build();
    }
}
