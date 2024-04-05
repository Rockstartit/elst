package edu.kit.elst.course_implementation;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.core.Guards;
import edu.kit.elst.users.UserId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "mockups")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Mockup {
    @EmbeddedId
    private final MockupId id;

    @JoinColumn(name = "file_id")
    @OneToOne(optional = false, orphanRemoval = true, fetch = FetchType.LAZY)
    private UploadedFile file;

    private BuildingBlockVersion buildingBlockVersion;

    private UserId createdBy;
    private String description;

    public Mockup(BuildingBlockVersion buildingBlockVersion, UploadedFile file, UserId createdBy) {
        Guards.notNull(file, "file");
        Guards.notNull(createdBy, "createdBy");
        Guards.notNull(buildingBlockVersion, "buildingBlockVersion");

        this.id = Mockups.nextIdentity();
        this.file = file;
        this.createdBy = createdBy;
        this.buildingBlockVersion = buildingBlockVersion;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNullElse(description, "");
    }
}
