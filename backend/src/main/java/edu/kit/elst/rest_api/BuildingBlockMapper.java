package edu.kit.elst.rest_api;

public class BuildingBlockMapper {
    public static BuildingBlock mapToBuildingBlock(edu.kit.elst.building_blocks.BuildingBlock buildingBlock) {
        BuildingBlock dto = new BuildingBlock();

        dto.setId(buildingBlock.id().value());
        dto.setName(buildingBlock.details().name());
        dto.setDescription(buildingBlock.details().description());
        dto.setReleaseStatus(buildingBlock.releaseStatus());

        return dto;
    }
}
