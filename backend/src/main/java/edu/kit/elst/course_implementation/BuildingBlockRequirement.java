package edu.kit.elst.course_implementation;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.core.Guards;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "requirements")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BuildingBlockRequirement {
    @EmbeddedId
    private final RequirementId id;

    private BuildingBlockVersion buildingBlockVersion;

    @Enumerated(EnumType.ORDINAL)
    private RequirementType type;

    private String content;

    public BuildingBlockRequirement(BuildingBlockVersion buildingBlockVersion, RequirementType type, String content) {
        Guards.notNull(buildingBlockVersion, "buildingBlockVersion");

        this.id = Requirements.nextIdentity();
        this.buildingBlockVersion = buildingBlockVersion;

        setType(type);
        setContent(content);
    }

    public void setContent(String content) {
        Guards.notEmptyBlankOrNull(content, "content");

        this.content = content;
    }

    public void setType(RequirementType type) {
        Guards.notNull(type, "type");

        this.type = type;
    }
}
