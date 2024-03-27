package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlock;
import edu.kit.elst.building_blocks.BuildingBlockDetails;
import edu.kit.elst.building_blocks.BuildingBlockService;
import edut.kit.elst.rest_api.BuildingBlockApi;
import edut.kit.elst.rest_api.BuildingBlockVersion;
import edut.kit.elst.rest_api.ReleasedBuildingBlock;
import edut.kit.elst.rest_api.RequestBuildingBlockRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
public class BuildingBlockController implements BuildingBlockApi {
    private final BuildingBlockService buildingBlockService;

    @Override
    public ResponseEntity<List<ReleasedBuildingBlock>> getReleasedBuildingBlocks() {
        Collection<BuildingBlock> buildingBlocks = buildingBlockService.allReleasedBuildingBlocks();

        return ResponseEntity.ok(buildingBlocks.stream()
                .map(this::mapToReleasedBuildingBlock)
                .toList());
    }

    @Override
    public ResponseEntity<BuildingBlockVersion> requestBuildingBlock(RequestBuildingBlockRequest body) {
        edu.kit.elst.building_blocks.BuildingBlockVersion previousVersion = null;

        if (body.getPreviousVersion() != null) {
            previousVersion = mapToBuildingBlockVersion(body.getPreviousVersion());
        }

        BuildingBlockDetails buildingBlockDetails = new BuildingBlockDetails(
                body.getName(), body.getDescription());

        edu.kit.elst.building_blocks.BuildingBlockVersion buildingBlockVersion;
        if (previousVersion != null) {
            buildingBlockVersion = buildingBlockService.registerBuildingBlock(previousVersion, buildingBlockDetails);
        } else {
            buildingBlockVersion = buildingBlockService.registerBuildingBlock(buildingBlockDetails);
        }

        return ResponseEntity.ok(mapToBuildingBlockVersion(buildingBlockVersion));
    }

    private BuildingBlockVersion mapToBuildingBlockVersion(edu.kit.elst.building_blocks.BuildingBlockVersion version) {
        BuildingBlockVersion dto = new BuildingBlockVersion();

        dto.setBuildingBlockId(version.buildingBlockId());
        dto.setVersion(BigDecimal.valueOf(version.versionNumber()));

        return dto;
    }

    private edu.kit.elst.building_blocks.BuildingBlockVersion mapToBuildingBlockVersion(BuildingBlockVersion version) {
        return new edu.kit.elst.building_blocks.BuildingBlockVersion(
                version.getBuildingBlockId(), version.getVersion().longValue());
    }

    private ReleasedBuildingBlock mapToReleasedBuildingBlock(BuildingBlock buildingBlock) {
        ReleasedBuildingBlock dto = new ReleasedBuildingBlock();

        dto.setId(buildingBlock.version().buildingBlockId());
        dto.setVersion(BigDecimal.valueOf(buildingBlock.version().versionNumber()));
        dto.setName(buildingBlock.details().name());
        dto.setDescription(buildingBlock.details().description());

        return dto;
    }
}
