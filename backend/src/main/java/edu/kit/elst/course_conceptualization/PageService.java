package edu.kit.elst.course_conceptualization;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PageService {
    private final Pages pages;
    private final CourseUnits courseUnits;
    private final PageBuildingBlocks pageBuildingBlocks;

    public PageId createPage(CourseUnitId courseUnitId, String title) {
        CourseUnit courseUnit = courseUnits.getReferenceById(courseUnitId);

        Page page = new Page(courseUnit, title);
        pages.save(page);

        return page.id();
    }

    public void editPage(PageId pageId, String title) {
        Page page = pages.findById(pageId)
                .orElseThrow(() -> new PageNotFoundException(pageId));

        page.title(title);
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
}
