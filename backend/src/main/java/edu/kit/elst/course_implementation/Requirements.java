package edu.kit.elst.course_implementation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Requirements extends JpaRepository<BuildingBlockRequirement, RequirementId> {
    static RequirementId nextIdentity() {
        return new RequirementId(UUID.randomUUID());
    }
}
