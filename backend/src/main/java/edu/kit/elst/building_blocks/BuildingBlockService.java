package edu.kit.elst.building_blocks;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BuildingBlockService {
    private final BuildingBlocks buildingBlocks;

    public BuildingBlockVersion registerBuildingBlock(BuildingBlockDetails details) {
        BuildingBlockVersion version = buildingBlocks.newBuildingBlockVersion();

        BuildingBlock buildingBlock = new BuildingBlock(version, details);
        buildingBlocks.save(buildingBlock);

        return version;
    }

    public BuildingBlockVersion registerBuildingBlock(BuildingBlockVersion previousVersion) {
        BuildingBlock previousBuildingBlock = buildingBlocks.findById(previousVersion)
                .orElseThrow(() -> new BuildingBlockNotFoundException(previousVersion));

        BuildingBlockVersion version = buildingBlocks.incrementVersion(previousVersion);
        BuildingBlockDetails details = previousBuildingBlock.details();

        BuildingBlock buildingBlock = new BuildingBlock(version, details);
        buildingBlocks.save(buildingBlock);

        return version;
    }

    public void releaseBuildingBlock(BuildingBlockVersion version, List<Property> properties) {
        BuildingBlock buildingBlock = buildingBlocks.findById(version)
                .orElseThrow(() -> new BuildingBlockNotFoundException(version));

        buildingBlock.setProperties(properties);
        buildingBlock.release();
    }

    public Collection<BuildingBlock> allReleasedBuildingBlocks() {
        return buildingBlocks.findAllReleased();
    }
}
