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
public class BuildingBlockService {
    private final BuildingBlocks buildingBlocks;

    public BuildingBlockId registerBuildingBlock(BuildingBlockDetails details) {
        BuildingBlockId version = buildingBlocks.newBuildingBlockVersion();

        BuildingBlock buildingBlock = new BuildingBlock(version, details);
        buildingBlocks.save(buildingBlock);

        return version;
    }

    public BuildingBlockId registerBuildingBlock(BuildingBlockId previousVersion, BuildingBlockDetails details) {
        BuildingBlockId version = buildingBlocks.incrementVersion(previousVersion);

        BuildingBlock buildingBlock = new BuildingBlock(version, details);
        buildingBlocks.save(buildingBlock);

        return version;
    }

    public void releaseBuildingBlock(BuildingBlockId version, List<Property> properties) {
        BuildingBlock buildingBlock = buildingBlocks.findById(version)
                .orElseThrow(() -> new BuildingBlockNotFoundException(version));

        buildingBlock.setProperties(properties);
        buildingBlock.release();
    }

    public Collection<BuildingBlock> allBuildingBlocks() {
        return buildingBlocks.findAll();
    }

    public Collection<BuildingBlock> buildingBlocks(Set<BuildingBlockId> buildingBlockIds) {
        return buildingBlocks.findAllById(buildingBlockIds);
    }

    public Optional<BuildingBlock> buildingBlock(BuildingBlockId version) {
        return buildingBlocks.findById(version);
    }

    public void editBuildingBlock(BuildingBlockId version, BuildingBlockDetails details) {
        BuildingBlock buildingBlock = buildingBlock(version)
                .orElseThrow(() -> new BuildingBlockNotFoundException(version));

        buildingBlock.setDetails(details);
    }
}
