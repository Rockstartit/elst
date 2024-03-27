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

    public BuildingBlockVersion registerBuildingBlock(BuildingBlockDetails details) {
        BuildingBlockVersion version = buildingBlocks.newBuildingBlockVersion();

        BuildingBlock buildingBlock = new BuildingBlock(version, details);
        buildingBlocks.save(buildingBlock);

        return version;
    }

    public BuildingBlockVersion registerBuildingBlock(BuildingBlockVersion previousVersion, BuildingBlockDetails details) {
        BuildingBlockVersion version = buildingBlocks.incrementVersion(previousVersion);

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

    public Collection<BuildingBlock> allBuildingBlocks() {
        return buildingBlocks.findAll();
    }

    public Collection<BuildingBlock> buildingBlocks(Set<BuildingBlockVersion> buildingBlockVersions) {
        return buildingBlocks.findAllById(buildingBlockVersions);
    }

    public Optional<BuildingBlock> buildingBlock(BuildingBlockVersion version) {
        return buildingBlocks.findById(version);
    }

    public void editBuildingBlock(BuildingBlockVersion version, BuildingBlockDetails details) {
        BuildingBlock buildingBlock = buildingBlock(version)
                .orElseThrow(() -> new BuildingBlockNotFoundException(version));

        buildingBlock.setDetails(details);
    }
}
