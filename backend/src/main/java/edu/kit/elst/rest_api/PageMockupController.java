package edu.kit.elst.rest_api;

import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.course_conceptualization.PageMockupAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class PageMockupController implements PageMockupApi {
    private final PageMockupAppService pageMockupAppService;

    @Override
    public ResponseEntity<UUID> uploadMockup(UUID pageId, String description, MultipartFile file) {
        PageId aPageId = new PageId(pageId);

        MockupId mockupId = pageMockupAppService.uploadMockup(aPageId, file, description);

        return ResponseEntity.ok(mockupId.value());
    }

    @Override
    public ResponseEntity<Void> editMockup(UUID mockupId, EditMockupRequest body) {
        MockupId aMockupId = new MockupId(mockupId);

        pageMockupAppService.editMockupDescription(aMockupId, body.getDescription());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteMockup(UUID mockupId) {
        MockupId aMockupId = new MockupId(mockupId);

        pageMockupAppService.deleteMockup(aMockupId);

        return ResponseEntity.ok().build();
    }
}
