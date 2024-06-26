package edu.kit.elst.collaboration;

import java.util.Collection;

public record ReferencesToDiscussion(
        Collection<BuildingBlockReference> buildingBlockReferences,
        Collection<PageReference> pageReferences,
        Collection<MockupReference> mockupReferences
) {
}
