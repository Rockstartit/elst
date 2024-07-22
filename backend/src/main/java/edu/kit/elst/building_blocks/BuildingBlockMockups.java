package edu.kit.elst.building_blocks;

import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.core.shared.MockupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BuildingBlockMockups extends JpaRepository<BuildingBlockMockup, MockupId> {
    static MockupId nextIdentity() {
        return new MockupId(UUID.randomUUID());
    }

    Collection<BuildingBlockMockup> findAllByBuildingBlockId(BuildingBlockId buildingBlockId);

    Collection<BuildingBlockMockup> findAllByBuildingBlockIdIn(Set<BuildingBlockId> buildingBlockIds);
}
