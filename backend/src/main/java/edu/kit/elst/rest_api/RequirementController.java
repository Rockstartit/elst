package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.course_implementation.RequirementId;
import edu.kit.elst.course_implementation.RequirementsAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class RequirementController implements RequirementApi {
    private final RequirementsAppService requirementsAppService;

    @Override
    public ResponseEntity<UUID> documentRequirement(UUID buildingBlockId, BigDecimal versionNumber, DocumentRequirementRequest body) {
        BuildingBlockVersion version = BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockId, versionNumber);

        RequirementId requirementId
                = requirementsAppService.documentRequirement(version, body.getType(), body.getContent());

        return ResponseEntity.ok(requirementId.value());
    }

    @Override
    public ResponseEntity<Void> editRequirement(UUID requirementId, EditRequirementRequest body) {
        RequirementId aRequirementId = new RequirementId(requirementId);

        requirementsAppService.editRequirement(aRequirementId, body.getType(), body.getContent());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteRequirement(UUID requirementId) {
        RequirementId aRequirementId = new RequirementId(requirementId);

        requirementsAppService.deleteRequirement(aRequirementId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<BuildingBlockRequirement>> getAllRequirements(UUID buildingBlockId, BigDecimal versionNumber) {
        BuildingBlockVersion version = BuildingBlockMapper.mapToBuildingBlockVersion(buildingBlockId, versionNumber);

        Collection<edu.kit.elst.course_implementation.BuildingBlockRequirement> requirements
                = requirementsAppService.requirements(version);

        return ResponseEntity.ok(requirements.stream()
                .map(this::mapToBuildingBlockRequirement)
                .toList());
    }

    private BuildingBlockRequirement mapToBuildingBlockRequirement(edu.kit.elst.course_implementation.BuildingBlockRequirement requirement) {
        BuildingBlockRequirement dto = new BuildingBlockRequirement();

        dto.setId(requirement.id().value());
        dto.setType(requirement.type());
        dto.setContent(requirement.content());

        return dto;
    }
}
