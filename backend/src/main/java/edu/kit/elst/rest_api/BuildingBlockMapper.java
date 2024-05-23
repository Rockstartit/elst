package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockId;

import java.math.BigDecimal;
import java.util.UUID;

public class BuildingBlockMapper {
    public static BuildingBlockVersion mapToBuildingBlockVersion(BuildingBlockId version) {
        BuildingBlockVersion dto = new BuildingBlockVersion();

        dto.setBuildingBlockId(version.value());
        dto.setVersion(BigDecimal.valueOf(version.versionNumber()));

        return dto;
    }

    public static BuildingBlockId mapToBuildingBlockVersion(BuildingBlockVersion version) {
        return new BuildingBlockId(
                version.getBuildingBlockId(), version.getVersion().longValue());
    }

    public static BuildingBlock mapToBuildingBlock(edu.kit.elst.building_blocks.BuildingBlock buildingBlock) {
        BuildingBlock dto = new BuildingBlock();

        dto.setVersion(mapToBuildingBlockVersion(buildingBlock.version()));
        dto.setName(buildingBlock.details().name());
        dto.setDescription(buildingBlock.details().description());
        dto.setReleaseStatus(buildingBlock.releaseStatus());

        return dto;
    }

    public static BuildingBlockId mapToBuildingBlockVersion(UUID buildingBlockId, BigDecimal versionNumber) {
        return new BuildingBlockId(buildingBlockId, versionNumber.longValue());
    }
}
