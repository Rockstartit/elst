package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlock;
import edu.kit.elst.building_blocks.BuildingBlockService;
import edut.kit.elst.rest_api.BuildingBlockApi;
import edut.kit.elst.rest_api.ReleasedBuildingBlock;
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

    private ReleasedBuildingBlock mapToReleasedBuildingBlock(BuildingBlock buildingBlock) {
        ReleasedBuildingBlock dto = new ReleasedBuildingBlock();

        dto.setId(buildingBlock.version().buildingBlockId());
        dto.setVersion(BigDecimal.valueOf(buildingBlock.version().versionNumber()));
        dto.setName(buildingBlock.details().name());
        dto.setDescription(buildingBlock.details().description());

        return dto;
    }
}
