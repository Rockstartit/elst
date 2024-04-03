package edu.kit.elst.rest_api;

import edut.kit.elst.rest_api.BuildingBlock;
import edut.kit.elst.rest_api.BuildingBlockVersion;

import java.math.BigDecimal;
import java.util.UUID;

public class BuildingBlockMapper {
    public static BuildingBlockVersion mapToBuildingBlockVersion(edu.kit.elst.building_blocks.BuildingBlockVersion version) {
        BuildingBlockVersion dto = new BuildingBlockVersion();

        dto.setBuildingBlockId(version.buildingBlockId());
        dto.setVersion(BigDecimal.valueOf(version.versionNumber()));

        return dto;
    }

    public static edu.kit.elst.building_blocks.BuildingBlockVersion mapToBuildingBlockVersion(BuildingBlockVersion version) {
        return new edu.kit.elst.building_blocks.BuildingBlockVersion(
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

    public static edu.kit.elst.building_blocks.BuildingBlockVersion mapToBuildingBlockVersion(UUID buildingBlockId, BigDecimal versionNumber) {
        return new edu.kit.elst.building_blocks.BuildingBlockVersion(buildingBlockId, versionNumber.longValue());
    }
}
