package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlock;
import edu.kit.elst.building_blocks.BuildingBlockService;
import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.core.shared.CourseId;
import edu.kit.elst.core.shared.PageBuildingBlockId;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.core.shared.TeachingPhaseId;
import edu.kit.elst.course_conceptualization.PageAppService;
import edu.kit.elst.course_conceptualization.PageBuildingBlock;
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
    private final BuildingBlockService buildingBlockService;

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

        pageAppService.editPage(aPageId, body.getTitle());

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Collection<PageBuildingBlock> pageBuildingBlocks = pageAppService.pageBuildingBlocks(aPageId);
        Set<BuildingBlockVersion> buildingBlockVersions = pageBuildingBlocks.stream()
                .map(PageBuildingBlock::version)
                .collect(Collectors.toSet());
        Map<BuildingBlockVersion, BuildingBlock> buildingBlockMap
                = buildingBlockService.buildingBlocks(buildingBlockVersions).stream()
                .collect(Collectors.toMap(BuildingBlock::version, Function.identity()));

        Collection<edu.kit.elst.course_conceptualization.Page> linkedPages = pageAppService.linkedPages(aPageId);

        return ResponseEntity.ok(mapToPage(page, linkedPages, pageBuildingBlocks, buildingBlockMap));
    }

    @Override
    public ResponseEntity<List<PageOverview>> getPages(UUID courseId) {
        CourseId aCourseId = new CourseId(courseId);

        Collection<edu.kit.elst.course_conceptualization.Page> pages
                = pageAppService.pages(aCourseId);

        return ResponseEntity.ok(pages.stream()
                .map(this::mapToPageOverview)
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
    public ResponseEntity<UUID> addBuildingBlockToPage(UUID pageId, AddBuildingBlockToPageRequest body) {
        PageId aPageId = new PageId(pageId);
        BuildingBlockVersion buildingBlockVersion
                = BuildingBlockMapper.mapToBuildingBlockVersion(body.getBuildingBlockId(), body.getVersion());

        PageBuildingBlockId pageBuildingBlockId = pageAppService.addBuildingBlockToPage(aPageId, buildingBlockVersion);

        return ResponseEntity.ok(pageBuildingBlockId.value());
    }

    @Override
    public ResponseEntity<UUID> removeBuildingBlockFromPage(UUID pageBuildingBlockId) {
        PageBuildingBlockId aPageBuildingBlockId = new PageBuildingBlockId(pageBuildingBlockId);

        pageAppService.removeBuildingBlockFromPage(aPageBuildingBlockId);

        return ResponseEntity.ok().build();
    }

    private Page mapToPage(edu.kit.elst.course_conceptualization.Page page,
                           Collection<edu.kit.elst.course_conceptualization.Page> linkedPages,
                           Collection<PageBuildingBlock> pageBuildingBlocks,
                           Map<BuildingBlockVersion, BuildingBlock> buildingBlockMap) {
        Page dto = new Page();

        dto.setId(page.id().value());
        dto.setTitle(page.title());
        dto.setTeachingPhaseId(page.teachingPhaseId().value());
        dto.setLinkedPages(linkedPages.stream()
                .map(this::mapToPageOverview)
                .toList());
        dto.setBuildingBlocks(pageBuildingBlocks.stream()
                .map(pageBuildingBlock -> mapToPageBuildingBlock(
                        pageBuildingBlock, buildingBlockMap.get(pageBuildingBlock.version())))
                .toList());

        return dto;
    }

    private edu.kit.elst.rest_api.PageBuildingBlock mapToPageBuildingBlock(PageBuildingBlock pageBuildingBlock, BuildingBlock buildingBlock) {
        edu.kit.elst.rest_api.PageBuildingBlock dto = new edu.kit.elst.rest_api.PageBuildingBlock();

        dto.setPageBuildingBlockId(pageBuildingBlock.id().value());
        dto.setVersion(BuildingBlockMapper.mapToBuildingBlockVersion(pageBuildingBlock.version()));
        dto.setName(buildingBlock.details().name());
        dto.setDescription(buildingBlock.details().description());
        dto.setReleaseStatus(buildingBlock.releaseStatus());

        return dto;
    }

    private PageOverview mapToPageOverview(edu.kit.elst.course_conceptualization.Page page) {
        PageOverview dto = new PageOverview();

        dto.setId(page.id().value());
        dto.setTitle(page.title());
        dto.setTeachingPhaseId(page.teachingPhaseId().value());

        return dto;
    }
}
