package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.FileId;
import edu.kit.elst.content_upload.StorageService;
import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.PageId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class MockupAppService {
    private final Mockups mockups;
    private final StorageService storageService;

    public MockupId uploadMockup(PageId pageId, MultipartFile file, String description) {
        FileId fileId = storageService.storeFile(file);

        Mockup mockup = new Mockup(pageId, fileId, UserContext.getUserId());
        mockup.description(description);

        mockups.save(mockup);

        return mockup.id();
    }

    public void editMockupDescription(MockupId mockupId, String description) {
        Mockup mockup = mockup(mockupId)
                .orElseThrow(() -> new MockupNotFoundException(mockupId));

        mockup.description(description);
    }

    public void deleteMockup(MockupId mockupId) {
        mockups.deleteById(mockupId);
    }

    public Collection<Mockup> mockupsByPageId(PageId pageId) {
        return mockups.findAllByPageId(pageId);
    }

    private Optional<Mockup> mockup(MockupId mockupId) {
        return mockups.findById(mockupId);
    }

    public Collection<Mockup> mockupsByPageId(Set<PageId> pageIds) {
        return mockups.findAllByPageIdIn(pageIds);
    }

    public Collection<Mockup> mockups(Set<MockupId> mockupIds) {
        return mockups.findAllById(mockupIds);
    }
}
