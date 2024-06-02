package edu.kit.elst.course_conceptualization;

import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.core.shared.CourseId;
import edu.kit.elst.core.shared.PageBuildingBlockId;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.core.shared.TeachingPhaseId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class PageAppService {
    private final Pages pages;
    private final Courses courses;
    private final PageLinks pageLinks;
    private final PageBuildingBlocks pageBuildingBlocks;

    public PageId createPage(CourseId courseId, TeachingPhaseId teachingPhaseId, String title) {
        Course course = courses.getReferenceById(courseId);
        Long maxOrder = pages.maxOrderByCourse(course);
        long order = maxOrder != null ? maxOrder + 1 : 0;

        Page page = new Page(course, teachingPhaseId, title);
        page.order(order);
        pages.save(page);

        return page.id();
    }

    public void editPageTitle(PageId pageId, String title) {
        Page page = pages.findById(pageId)
                .orElseThrow(() -> new PageNotFoundException(pageId));

        page.title(title);
    }

    public void editPageNotes(PageId pageId, String notes) {
        Page page = pages.findById(pageId)
                .orElseThrow(() -> new PageNotFoundException(pageId));

        page.notes(notes);
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

    public PageBuildingBlockId addBuildingBlockToPage(PageId pageId, BuildingBlockId id) {
        Page page = pages.getReferenceById(pageId);

        PageBuildingBlock buildingBlock = new PageBuildingBlock(page, id);
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

    public Collection<Page> linkedPages(PageId pageId) {
        return pages.findAllLinkedTo(pageId);
    }

    public Collection<Page> pages(CourseId courseId, Set<TeachingPhaseId> teachingPhaseIds) {
        Course course = courses.getReferenceById(courseId);

        return pages.findAllByCourseAndTeachingPhaseIdIn(course, teachingPhaseIds);
    }

    public Collection<PageBuildingBlock> pageBuildingBlocks(Set<PageId> pageIds) {
        return pageBuildingBlocks.findAllByPageIdIn(pageIds);
    }

    public Collection<Page> pages(Set<PageId> pageIds) {
        return pages.findAllById(pageIds);
    }

    public void reorderPages(CourseId courseId, List<PageId> pageIds) {
        Course course = courses.getReferenceById(courseId);

        Collection<Page> pageList = pages.findAllByCourse(course);

        for (Page page : pageList) {
            page.order(pageIds.indexOf(page.id()));
        }
    }

    public Optional<PageBuildingBlock> pageBuildingBlock(PageBuildingBlockId pageBuildingBlockId) {
        return pageBuildingBlocks.findById(pageBuildingBlockId);
    }
}
