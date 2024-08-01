package edu.kit.elst.course_planning;

import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.PageBuildingBlockId;
import edu.kit.elst.core.shared.PageId;
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

    private BuildingBlockId buildingBlockId;

    @Column(name = "order_index")
    private long order;

    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "page_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Page page;

    public PageBuildingBlock(Page page, BuildingBlockId buildingBlockId) {
        Guards.notNull(page, "page");
        Guards.notNull(buildingBlockId, "buildingBlockId");

        this.id = PageBuildingBlocks.nextIdentity();
        this.buildingBlockId = buildingBlockId;
        this.page = page;
        this.order = 0;
    }

    public void order(long order) {
        this.order = order;
    }

    public PageId pageId() {
        return page.id();
    }
}
