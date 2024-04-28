package edu.kit.elst.course_conceptualization;

import edu.kit.elst.content_upload.FileId;
import edu.kit.elst.content_upload.StorageService;
import edu.kit.elst.core.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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

    private Optional<Mockup> mockup(MockupId mockupId) {
        return mockups.findById(mockupId);
    }
}
