package edu.kit.elst.building_blocks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

interface BuildingBlocks extends JpaRepository<BuildingBlock, BuildingBlockId> {
    default BuildingBlockId newBuildingBlockVersion() {
        return new BuildingBlockId(UUID.randomUUID(), 1);
    }

    default BuildingBlockId incrementVersion(BuildingBlockId buildingBlockId) {
        Optional<Long> highestVersionOptional = highestVersion(buildingBlockId.value());

        long version = highestVersionOptional
                .map(highestVersion -> highestVersion + 1)
                .orElse(1L);

        return new BuildingBlockId(buildingBlockId.value(), version);
    }

    @Query("select buildingBlock.version.versionNumber from BuildingBlock buildingBlock " +
            "where buildingBlock.version.buildingBlockId = :buildingBlockId")
    Optional<Long> highestVersion(UUID buildingBlockId);
}
