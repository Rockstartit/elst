package edu.kit.elst.building_blocks;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BuildingBlockPropertyAppService {
    private final BuildingBlocks buildingBlocks;
    private final BuildingBlockProperties buildingBlockProperties;

    public void createBuildingBlockProperty(BuildingBlockId buildingBlockId, String key,
                                            String displayName, String description, BuildingBlockPropertyType type) {
        BuildingBlock buildingBlock = buildingBlocks.getReferenceById(buildingBlockId);
        Long maxOrder = buildingBlockProperties.maxOrderByBuildingBlock(buildingBlock);
        long order = maxOrder != null ? maxOrder + 1 : 0;

        BuildingBlockProperty property = new BuildingBlockProperty(buildingBlock, key, displayName);
        property.description(description);
        property.type(type);
        property.order(order);

        buildingBlockProperties.save(property);
    }

    public void editBuildingBlockProperty(BuildingBlockPropertyId propertyId, String displayName, String description, BuildingBlockPropertyType type) {
        BuildingBlockProperty property = buildingBlockProperty(propertyId)
                .orElseThrow();

        property.displayName(displayName);
        property.description(description);
        property.type(type);
    }

    public void deleteBuildingBlockProperty(BuildingBlockPropertyId propertyId) {
        buildingBlockProperties.deleteById(propertyId);
    }

    private Optional<BuildingBlockProperty> buildingBlockProperty(BuildingBlockPropertyId id) {
        return buildingBlockProperties.findById(id);
    }
}
