package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.FileId;
import edu.kit.elst.content_upload.StorageService;
import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.MockupNotFoundException;
import edu.kit.elst.core.shared.PageId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class PageMockupAppService {
    private final PageMockups pageMockups;
    private final StorageService storageService;

    public MockupId uploadMockup(PageId pageId, MultipartFile file, String description) {
        FileId fileId = storageService.storeFile(file);

        PageMockup mockup = new PageMockup(pageId, fileId, UserContext.getUserId());

        if (StringUtils.hasText(description)) {
            mockup.description(description);
        } else {
            mockup.description(file.getOriginalFilename());
        }

        pageMockups.save(mockup);

        return mockup.id();
    }

    public void editMockupDescription(MockupId mockupId, String description) {
        PageMockup mockup = mockup(mockupId)
                .orElseThrow(() -> new MockupNotFoundException(mockupId));

        mockup.description(description);
    }

    public void deleteMockup(MockupId mockupId) {
        pageMockups.deleteById(mockupId);
    }

    public Collection<PageMockup> mockupsByPageId(PageId pageId) {
        return pageMockups.findAllByPageId(pageId);
    }

    private Optional<PageMockup> mockup(MockupId mockupId) {
        return pageMockups.findById(mockupId);
    }

    public Collection<PageMockup> mockupsByPageId(Set<PageId> pageIds) {
        return pageMockups.findAllByPageIdIn(pageIds);
    }

    public Collection<PageMockup> pageMockups(Set<MockupId> mockupIds) {
        return pageMockups.findAllById(mockupIds);
    }
}
