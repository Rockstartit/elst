package edu.kit.elst.course_conceptualization;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.core.Guards;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "page_building_blocks")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PageBuildingBlock {
    @EmbeddedId
    private final PageBuildingBlockId id;

    private BuildingBlockVersion version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Page page;

    public PageBuildingBlock(Page page, BuildingBlockVersion version) {
        Guards.notNull(page, "page");
        Guards.notNull(version, "version");

        this.id = PageBuildingBlocks.nextIdentity();
        this.version = version;
        this.page = page;
    }
}
