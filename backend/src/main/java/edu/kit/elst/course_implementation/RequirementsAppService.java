package edu.kit.elst.course_implementation;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
@AllArgsConstructor
public class RequirementsAppService {
    private final Requirements requirements;

    public RequirementId documentRequirement(BuildingBlockVersion buildingBlockVersion, RequirementType type, String content) {
        BuildingBlockRequirement requirement = new BuildingBlockRequirement(buildingBlockVersion, type, content);

        requirements.save(requirement);

        return requirement.id();
    }

    public void editRequirement(RequirementId requirementId, RequirementType type, String content) {
        BuildingBlockRequirement requirement = requirements.findById(requirementId)
                .orElseThrow(() -> new RequirementNotFoundException(requirementId));

        requirement.setType(type);
        requirement.setContent(content);
    }

    public void deleteRequirement(RequirementId requirementId) {
        requirements.deleteById(requirementId);
    }

    public Collection<BuildingBlockRequirement> requirements(BuildingBlockVersion buildingBlockVersion) {
        return requirements.findAllByBuildingBlockVersion(buildingBlockVersion);
    }
}
