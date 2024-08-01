package edu.kit.elst.course_planning;

import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.PageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PageMockups extends JpaRepository<PageMockup, MockupId> {
    static MockupId nextIdentity() {
        return new MockupId(UUID.randomUUID());
    }

    Collection<PageMockup> findAllByPageId(PageId pageId);

    Collection<PageMockup> findAllByPageIdIn(Set<PageId> pageIds);
}
