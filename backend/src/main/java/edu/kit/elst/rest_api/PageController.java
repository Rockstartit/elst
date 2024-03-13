package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.course_conceptualization.CourseUnitId;
import edu.kit.elst.course_conceptualization.PageBuildingBlockId;
import edu.kit.elst.course_conceptualization.PageId;
import edu.kit.elst.course_conceptualization.PageService;
import edut.kit.elst.rest_api.AddBuildingBlockToPageRequest;
import edut.kit.elst.rest_api.AddPageRequest;
import edut.kit.elst.rest_api.EditPageRequest;
import edut.kit.elst.rest_api.PageApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class PageController implements PageApi {
    private final PageService pageService;

    @Override
    public ResponseEntity<UUID> createPage(UUID courseUnitId, AddPageRequest body) {
        CourseUnitId aCourseUnitId = new CourseUnitId(courseUnitId);

        PageId pageId = pageService.createPage(aCourseUnitId, body.getTitle());

        return ResponseEntity.ok(pageId.value());
    }

    @Override
    public ResponseEntity<Void> editPage(UUID pageId, EditPageRequest body) {
        PageId aPageId = new PageId(pageId);

        pageService.editPage(aPageId, body.getTitle());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deletePage(UUID pageId) {
        PageId aPageId = new PageId(pageId);

        pageService.deletePage(aPageId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UUID> addBuildingBlockToPage(UUID pageId, AddBuildingBlockToPageRequest body) {
        PageId aPageId = new PageId(pageId);
        BuildingBlockVersion version
                = new BuildingBlockVersion(body.getBuildingBlockId(), body.getVersion().longValue());

        PageBuildingBlockId pageBuildingBlockId = pageService.addBuildingBlockToPage(aPageId, version);

        return ResponseEntity.ok(pageBuildingBlockId.value());
    }

    @Override
    public ResponseEntity<UUID> removeBuildingBlockFromPage(UUID pageBuildingBlockId) {
        PageBuildingBlockId aPageBuildingBlockId = new PageBuildingBlockId(pageBuildingBlockId);

        pageService.removeBuildingBlockFromPage(aPageBuildingBlockId);

        return ResponseEntity.ok().build();
    }
}
