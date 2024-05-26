package edu.kit.elst.building_blocks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

interface BuildingBlocks extends JpaRepository<BuildingBlock, BuildingBlockId> {
    default BuildingBlockId nextIdentity() {
        return new BuildingBlockId(UUID.randomUUID());
    }

    @Query("select buildingBlock from BuildingBlock buildingBlock " +
            "left join fetch buildingBlock.properties " +
            "where buildingBlock.id = :id")
    Optional<BuildingBlock> findWithPropertiesById(BuildingBlockId id);

    @Query("select buildingBlock from BuildingBlock buildingBlock " +
            "left join fetch buildingBlock.properties")
    Collection<BuildingBlock> findAllWithProperties();
}
