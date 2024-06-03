package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.PageBuildingBlockId;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.lesson_planning.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
interface PageBuildingBlocks extends CrudRepository<PageBuildingBlock, PageBuildingBlockId> {
    static PageBuildingBlockId nextIdentity() {
        return new PageBuildingBlockId(UUID.randomUUID());
    }

    Collection<PageBuildingBlock> findAllByPage(Page page);

    @Query("select pageBuildingBlock from PageBuildingBlock pageBuildingBlock " +
            "join fetch pageBuildingBlock.page " +
            "where pageBuildingBlock.page.id in :pageIds")
    Collection<PageBuildingBlock> findAllByPageIdIn(Set<PageId> pageIds);

    @Query("select max(pageBuildingBlock.order) from PageBuildingBlock pageBuildingBlock " +
            "where pageBuildingBlock.page = :page " +
            "group by pageBuildingBlock.page.id ")
    Long maxOrderByPage(Page page);
}
