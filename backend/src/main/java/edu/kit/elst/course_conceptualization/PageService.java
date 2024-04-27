package edu.kit.elst.course_conceptualization;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.lesson_planning.TeachingPhaseId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PageService {
    private final Pages pages;
    private final Courses courses;
    private final PageLinks pageLinks;
    private final PageBuildingBlocks pageBuildingBlocks;

    public PageId createPage(CourseId courseId, TeachingPhaseId teachingPhaseId, String title) {
        Course course = courses.getReferenceById(courseId);

        Page page = new Page(course, teachingPhaseId, title);
        pages.save(page);

        return page.id();
    }

    public void editPage(PageId pageId, String title) {
        Page page = pages.findById(pageId)
                .orElseThrow(() -> new PageNotFoundException(pageId));

        page.title(title);
    }

    public void linkPages(PageId pageId, PageId targetPageId) {
        PageLink pageLink = new PageLink(pageId, targetPageId);

        pageLinks.deleteByPageIdAndTargetPageId(pageId, targetPageId);
        pageLinks.save(pageLink);
    }

    public void unlinkPages(PageId pageId, PageId targetPageId) {
        pageLinks.deleteByPageIdAndTargetPageId(pageId, targetPageId);
    }

    public void deletePage(PageId pageId) {
        pages.deleteById(pageId);
    }

    public PageBuildingBlockId addBuildingBlockToPage(PageId pageId, BuildingBlockVersion version) {
        Page page = pages.getReferenceById(pageId);

        PageBuildingBlock buildingBlock = new PageBuildingBlock(page, version);
        pageBuildingBlocks.save(buildingBlock);

        return buildingBlock.id();
    }

    public void removeBuildingBlockFromPage(PageBuildingBlockId buildingBlockId) {
        pageBuildingBlocks.deleteById(buildingBlockId);
    }

    public Collection<Page> pages(CourseId courseId) {
        Course course = courses.getReferenceById(courseId);

        return pages.findAllByCourse(course);
    }

    public Optional<Page> page(PageId pageId) {
        return pages.findById(pageId);
    }

    public Collection<PageBuildingBlock> pageBuildingBlocks(PageId pageId) {
        Page page = pages.getReferenceById(pageId);

        return pageBuildingBlocks.findAllByPage(page);
    }
}
