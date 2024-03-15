package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.course_conceptualization.PageBuildingBlock;
import edu.kit.elst.course_conceptualization.*;
import edut.kit.elst.rest_api.Page;
import edut.kit.elst.rest_api.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
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

    @Override
    public ResponseEntity<Page> getPage(UUID pageId) {
        PageId aPageId = new PageId(pageId);

        edu.kit.elst.course_conceptualization.Page page
                = pageService.page(aPageId)
                .orElseThrow(() -> new PageNotFoundException(aPageId));
        Collection<PageBuildingBlock> buildingBlocks = pageService.pageBuildingBlocks(aPageId);

        return ResponseEntity.ok(mapToPage(page, buildingBlocks));
    }

    @Override
    public ResponseEntity<List<PageOverview>> getPages(UUID courseUnitId) {
        CourseUnitId aCourseUnitId = new CourseUnitId(courseUnitId);

        Collection<edu.kit.elst.course_conceptualization.Page> pages
                = pageService.pages(aCourseUnitId);

        return ResponseEntity.ok(pages.stream()
                .map(this::mapToPageOverview)
                .toList());
    }

    private PageOverview mapToPageOverview(edu.kit.elst.course_conceptualization.Page page) {
        PageOverview dto = new PageOverview();

        dto.setId(page.id().value());
        dto.setTitle(page.title());

        return dto;
    }

    private Page mapToPage(edu.kit.elst.course_conceptualization.Page page, Collection<PageBuildingBlock> buildingBlocks) {
        Page dto = new Page();

        dto.setId(page.id().value());
        dto.setTitle(page.title());
        dto.setBuildingBlocks(buildingBlocks.stream()
                .map(this::mapToPageBuildingBlock)
                .toList());

        return dto;
    }

    private edut.kit.elst.rest_api.PageBuildingBlock mapToPageBuildingBlock(PageBuildingBlock buildingBlock) {
        edut.kit.elst.rest_api.PageBuildingBlock dto = new edut.kit.elst.rest_api.PageBuildingBlock();

        dto.setPageBuildingBlockId(buildingBlock.id().value());
        dto.setBuildingBlockId(buildingBlock.version().buildingBlockId());
        dto.setVersion(BigDecimal.valueOf(buildingBlock.version().versionNumber()));

        return dto;
    }
}
