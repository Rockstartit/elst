package edu.kit.elst.collaboration;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.MockupId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class MockupReference extends DiscussionReference {
    private final MockupId mockupId;

    public MockupReference(Discussion discussion, MockupId mockupId) {
        super(discussion);

        Guards.notNull(mockupId, "mockupId");
        this.mockupId = mockupId;
    }
}
