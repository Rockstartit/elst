package edu.kit.elst.collaboration;

import java.util.Collection;

public record ReferencesToDiscussion(
        Collection<CourseReference> courseReferences,
        Collection<PageReference> pageReferences,
        Collection<MockupReference> mockupReferences
) {
}
