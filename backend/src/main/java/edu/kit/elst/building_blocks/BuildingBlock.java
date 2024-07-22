package edu.kit.elst.building_blocks;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.BuildingBlockId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Table(name = "building_blocks")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BuildingBlock {
    @EmbeddedId
    private final BuildingBlockId id;

    @Embedded
    private BuildingBlockDetails details;

    @Embedded
    private Technology technology;

    private ReleaseStatus releaseStatus;

    @OneToMany(orphanRemoval = true, mappedBy = "buildingBlock")
    private Collection<BuildingBlockProperty> properties;

    BuildingBlock(BuildingBlockId id, Technology technology, BuildingBlockDetails details) {
        Guards.notNull(id, "id");

        this.id = id;
        this.releaseStatus = ReleaseStatus.IN_DEVELOPMENT;
        this.properties = new ArrayList<>();

        setDetails(details);
        setTechnology(technology);
    }

    void release() {
        this.releaseStatus = ReleaseStatus.RELEASED;
    }

    public Collection<BuildingBlockProperty> properties() {
        return Collections.unmodifiableCollection(properties);
    }

    public void setDetails(BuildingBlockDetails details) {
        Guards.notNull(details, "details");

        this.details = details;
    }

    public void setTechnology(Technology technology) {
        Guards.notNull(technology, "technology");

        this.technology = technology;
    }
}
