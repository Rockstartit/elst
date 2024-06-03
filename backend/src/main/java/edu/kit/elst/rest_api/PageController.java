package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlock;
import edu.kit.elst.building_blocks.BuildingBlockAppService;
import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.core.shared.*;
import edu.kit.elst.course_conceptualization.*;
import edu.kit.elst.course_conceptualization.PageBuildingBlock;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class PageController implements PageApi {
    private final PageAppService pageAppService;
    private final PageMockupAppService pageMockupAppService;
    private final BuildingBlockAppService buildingBlockService;
    private final PageBuildingBlockAppService pageBuildingBlockAppService;

    @Override
    public ResponseEntity<UUID> createPage(UUID courseId, CreatePageRequest body) {
        CourseId aCourseId = new CourseId(courseId);
        TeachingPhaseId aTeachingPhaseId = new TeachingPhaseId(body.getTeachingPhaseId());

        PageId pageId = pageAppService.createPage(aCourseId, aTeachingPhaseId, body.getTitle());

        return ResponseEntity.ok(pageId.value());
    }

    @Override
    public ResponseEntity<Void> editPage(UUID pageId, EditPageRequest body) {
        PageId aPageId = new PageId(pageId);

        if (body.getTitle() != null) {
            pageAppService.editPageTitle(aPageId, body.getTitle());
        }

        if (body.getNotes() != null) {
            pageAppService.editPageNotes(aPageId, body.getNotes());
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deletePage(UUID pageId) {
        PageId aPageId = new PageId(pageId);

        pageAppService.deletePage(aPageId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Page> getPage(UUID pageId) {
        PageId aPageId = new PageId(pageId);

        edu.kit.elst.course_conceptualization.Page page = pageAppService.page(aPageId)
                .orElseThrow(() -> new PageNotFoundException(aPageId));

        Collection<PageBuildingBlock> pageBuildingBlocks = pageBuildingBlockAppService.pageBuildingBlocks(aPageId);
        Set<BuildingBlockId> buildingBlockIds = pageBuildingBlocks.stream()
                .map(PageBuildingBlock::buildingBlockId)
                .collect(Collectors.toSet());
        Map<BuildingBlockId, BuildingBlock> buildingBlockMap
                = buildingBlockService.buildingBlocks(buildingBlockIds).stream()
                .collect(Collectors.toMap(BuildingBlock::id, Function.identity()));
        Collection<PageMockup> mockups = pageMockupAppService.mockupsByPageId(aPageId);

        Collection<edu.kit.elst.course_conceptualization.Page> linkedPages = pageAppService.linkedPages(aPageId);

        return ResponseEntity.ok(CourseMapper.mapToPage(page, linkedPages, mockups, pageBuildingBlocks, buildingBlockMap));
    }

    @Override
    public ResponseEntity<List<PageOverview>> getPages(UUID courseId) {
        CourseId aCourseId = new CourseId(courseId);

        Collection<edu.kit.elst.course_conceptualization.Page> pages
                = pageAppService.pages(aCourseId);

        return ResponseEntity.ok(pages.stream()
                .map(CourseMapper::mapToPageOverview)
                .toList());
    }

    @Override
    public ResponseEntity<Void> linkPages(LinkPagesRequest body) {
        PageId aPageId = new PageId(body.getPageId());
        PageId aTargetPageId = new PageId(body.getTargetPageId());

        pageAppService.linkPages(aPageId, aTargetPageId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removePageLink(UUID pageId, UUID targetPageId) {
        PageId aPageId = new PageId(pageId);
        PageId aTargetPageId = new PageId(targetPageId);

        pageAppService.linkPages(aPageId, aTargetPageId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> reorderPages(UUID courseId, List<UUID> pageIds) {
        CourseId aCourseId = new CourseId(courseId);
        List<PageId> thePageIds = pageIds.stream()
                .map(PageId::new)
                .toList();

        pageAppService.reorderPages(aCourseId, thePageIds);

        return ResponseEntity.ok().build();
    }
}
