package edu.kit.elst.building_blocks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

interface BuildingBlocks extends JpaRepository<BuildingBlock, BuildingBlockVersion> {
    default BuildingBlockVersion newBuildingBlockVersion() {
        return new BuildingBlockVersion(UUID.randomUUID(), 1);
    }

    default BuildingBlockVersion incrementVersion(BuildingBlockVersion buildingBlockVersion) {
        Optional<Long> highestVersionOptional = highestVersion(buildingBlockVersion.buildingBlockId());

        long version = highestVersionOptional
                .map(highestVersion -> highestVersion + 1)
                .orElse(1L);

        return new BuildingBlockVersion(buildingBlockVersion.buildingBlockId(), version);
    }

    @Query("select buildingBlock.version.versionNumber from BuildingBlock buildingBlock " +
            "where buildingBlock.version.buildingBlockId = :buildingBlockId")
    Optional<Long> highestVersion(UUID buildingBlockId);
}
