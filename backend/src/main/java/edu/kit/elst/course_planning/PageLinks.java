package edu.kit.elst.course_planning;

import edu.kit.elst.core.shared.PageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PageLinks extends JpaRepository<PageLink, PageLink> {
    void deleteByPageIdAndTargetPageId(PageId pageId, PageId targetPageId);

    Collection<PageLink> findAllByPageId(PageId pageId);

    Optional<PageLink> findByPageIdAndTargetPageId(PageId pageId, PageId targetPageId);
}
