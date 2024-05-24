package edu.kit.elst.building_blocks;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class BuildingBlockAppService {
    private final BuildingBlocks buildingBlocks;

    public BuildingBlockId registerBuildingBlock(BuildingBlockDetails details, Technology technology) {
        BuildingBlockId id = buildingBlocks.nextIdentity();

        BuildingBlock buildingBlock = new BuildingBlock(id, technology, details);
        buildingBlocks.save(buildingBlock);

        return id;
    }

    public void editBuildingBlock(BuildingBlockId id, BuildingBlockDetails details, Technology technology) {
        BuildingBlock buildingBlock = buildingBlock(id)
                .orElseThrow(() -> new BuildingBlockNotFoundException(id));

        buildingBlock.setDetails(details);
        buildingBlock.setTechnology(technology);
    }

    public void editBuildingBlockProperties(BuildingBlockId id, List<BuildingBlockProperty> properties) {
        BuildingBlock buildingBlock = buildingBlocks.findById(id)
                .orElseThrow(() -> new BuildingBlockNotFoundException(id));

        buildingBlock.setProperties(properties);
    }

    public void releaseBuildingBlock(BuildingBlockId id) {
        BuildingBlock buildingBlock = buildingBlocks.findById(id)
                .orElseThrow(() -> new BuildingBlockNotFoundException(id));

        buildingBlock.release();
    }

    public Collection<BuildingBlock> allBuildingBlocks() {
        return buildingBlocks.findAll();
    }

    public Collection<BuildingBlock> buildingBlocks(Set<BuildingBlockId> buildingBlockIds) {
        return buildingBlocks.findAllById(buildingBlockIds);
    }

    public Optional<BuildingBlock> buildingBlock(BuildingBlockId id) {
        return buildingBlocks.findWithPropertiesById(id);
    }
}
