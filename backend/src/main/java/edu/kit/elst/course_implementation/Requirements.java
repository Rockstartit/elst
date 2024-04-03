package edu.kit.elst.course_implementation;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface Requirements extends JpaRepository<BuildingBlockRequirement, RequirementId> {
    static RequirementId nextIdentity() {
        return new RequirementId(UUID.randomUUID());
    }

    Collection<BuildingBlockRequirement> findAllByBuildingBlockVersion(BuildingBlockVersion buildingBlockVersion);
}
