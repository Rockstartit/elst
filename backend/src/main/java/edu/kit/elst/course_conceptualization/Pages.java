package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.core.shared.TeachingPhaseId;
import edu.kit.elst.lesson_planning.TeachingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
interface Pages extends JpaRepository<Page, PageId> {
    static PageId nextIdentity() {
        return new PageId(UUID.randomUUID());
    }

    Collection<Page> findAllByCourse(Course course);

    @Query("select targetPage from PageLink pageLink " +
            "join Page targetPage on targetPage.id = pageLink.targetPageId " +
            "where pageLink.pageId = :pageId")
    Collection<Page> findAllLinkedTo(PageId pageId);

    @Query("select page from Page page " +
            "where page.course = :course " +
            "and page.teachingPhaseId in :teachingPhaseIds")
    Collection<Page> findAllByCourseAndTeachingPhaseIdIn(Course course, Set<TeachingPhaseId> teachingPhaseIds);

    @Query("select max(page.order) from Page page " +
            "where page.course = :course " +
            "group by page.course.id ")
    Long maxOrderByCourse(Course course);
}
