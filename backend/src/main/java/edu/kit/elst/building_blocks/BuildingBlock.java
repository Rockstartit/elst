package edu.kit.elst.building_blocks;

import edu.kit.elst.core.Guards;
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
    private final BuildingBlockId version;

    @Embedded
    private BuildingBlockDetails details;

    private ReleaseStatus releaseStatus;

    @ElementCollection
    @CollectionTable(
            name = "building_block_properties",
            joinColumns = {
                    @JoinColumn(name = "building_block_id", referencedColumnName = "building_block_id"),
                    @JoinColumn(name = "building_block_version_number", referencedColumnName = "building_block_version_number")
            })
    private Collection<Property> properties;

    BuildingBlock(BuildingBlockId version, BuildingBlockDetails details) {
        Guards.notNull(version, "version");
        Guards.notNull(details, "details");

        this.version = version;
        this.details = details;
        this.releaseStatus = ReleaseStatus.IN_DEVELOPMENT;
        this.properties = new ArrayList<>();
    }

    void setProperties(Collection<Property> properties) {
        this.properties.clear();

        if (properties != null) {
            this.properties.addAll(properties);
        }
    }

    void release() {
        this.releaseStatus = ReleaseStatus.RELEASED;
    }

    public Collection<Property> properties() {
        return Collections.unmodifiableCollection(properties);
    }

    public void setDetails(BuildingBlockDetails details) {
        Guards.notNull(details, "details");

        this.details = details;
    }
}
