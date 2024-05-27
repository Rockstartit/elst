package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockMockup;
import edu.kit.elst.building_blocks.BuildingBlockProperty;
import edu.kit.elst.course_conceptualization.PageMockup;

public class BuildingBlockMapper {
    public static BuildingBlock mapToBuildingBlock(edu.kit.elst.building_blocks.BuildingBlock buildingBlock) {
        BuildingBlock dto = new BuildingBlock();

        dto.setId(buildingBlock.id().value());
        dto.setName(buildingBlock.details().name());
        dto.setDescription(buildingBlock.details().description());
        dto.setTechnology(buildingBlock.technology().value());
        dto.setReleaseStatus(buildingBlock.releaseStatus());
        dto.setProperties(buildingBlock.properties().stream()
                .map(BuildingBlockMapper::mapToBuildingBlockProperty)
                .toList());

        return dto;
    }

    private static edu.kit.elst.rest_api.BuildingBlockProperty mapToBuildingBlockProperty(BuildingBlockProperty property) {
        edu.kit.elst.rest_api.BuildingBlockProperty dto = new edu.kit.elst.rest_api.BuildingBlockProperty();

        dto.setKey(property.key());
        dto.setDescription(property.description());
        dto.setDisplayName(property.displayName());
        dto.setOrder(UtilMapper.mapToBigDecimal(property.order()));
        dto.setType(property.type());

        return dto;
    }

    public static edu.kit.elst.building_blocks.BuildingBlockProperty mapToBuildingBlockProperty(edu.kit.elst.rest_api.BuildingBlockProperty dto) {
        edu.kit.elst.building_blocks.BuildingBlockProperty property = new edu.kit.elst.building_blocks.BuildingBlockProperty(dto.getKey(), dto.getDisplayName(), dto.getOrder().intValue());
        property.description(dto.getDescription());
        property.type(dto.getType());

        return property;
    }

    public static Mockup mapToMockup(BuildingBlockMockup mockup) {
        Mockup dto = new Mockup();

        dto.setId(mockup.id().value());
        dto.setFileId(mockup.fileId().value());
        dto.setDescription(mockup.description());

        return dto;
    }
}
