package edu.kit.elst.building_blocks;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.FileId;
import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.UserId;
import edu.kit.elst.course_conceptualization.PageMockups;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "building_block_mockups")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BuildingBlockMockup {
    @EmbeddedId
    private final MockupId id;

    private final BuildingBlockId buildingBlockId;
    private final FileId fileId;

    @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    private UserId createdBy;

    @Lob
    @Column(length = 256)
    private String description;

    public BuildingBlockMockup(BuildingBlockId buildingBlockId, FileId fileId, UserId createdBy) {
        Guards.notNull(fileId, "fileId");
        Guards.notNull(buildingBlockId, "buildingBlockId");
        Guards.notNull(createdBy, "createdBy");

        this.id = BuildingBlockMockups.nextIdentity();
        this.buildingBlockId = buildingBlockId;
        this.fileId = fileId;
        this.createdBy = createdBy;
    }

    public void description(String description) {
        this.description = Objects.requireNonNullElse(description, "");
    }
}
