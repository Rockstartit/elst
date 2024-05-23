package edu.kit.elst.building_blocks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface BuildingBlocks extends JpaRepository<BuildingBlock, BuildingBlockId> {
    default BuildingBlockId nextIdentity() {
        return new BuildingBlockId(UUID.randomUUID());
    }
}
