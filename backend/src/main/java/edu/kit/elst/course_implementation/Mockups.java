package edu.kit.elst.course_implementation;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface Mockups extends JpaRepository<Mockup, MockupId> {
    static MockupId nextIdentity() {
        return new MockupId(UUID.randomUUID());
    }

    @Query("select mockup.id from Mockup mockup " +
            "where mockup.buildingBlockVersion = :buildingBlockVersion")
    Collection<MockupId> findAllMockupsByBuildingBlockVersion(BuildingBlockVersion buildingBlockVersion);
}
