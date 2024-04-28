package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.PageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageLinks extends JpaRepository<PageLink, PageLink> {
    void deleteByPageIdAndTargetPageId(PageId pageId, PageId targetPageId);
}
