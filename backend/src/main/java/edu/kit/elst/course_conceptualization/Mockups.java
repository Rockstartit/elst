package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.PageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
public interface Mockups extends JpaRepository<Mockup, MockupId> {
    static MockupId nextIdentity() {
        return new MockupId(UUID.randomUUID());
    }

    Collection<Mockup> findAllByPageId(PageId pageId);

    Collection<Mockup> findAllByPageIdIn(Set<PageId> pageIds);
}
