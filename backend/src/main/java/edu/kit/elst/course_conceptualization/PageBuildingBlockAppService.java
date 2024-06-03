package edu.kit.elst.course_conceptualization;

import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.core.shared.PageBuildingBlockId;
import edu.kit.elst.core.shared.PageId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class PageBuildingBlockAppService {
    private final Pages pages;
    private final PageBuildingBlocks pageBuildingBlocks;
    private final PageBuildingBlockPropertyValues pageBuildingBlockPropertyValues;

    public PageBuildingBlockId addBuildingBlockToPage(PageId pageId, BuildingBlockId id) {
        Page page = pages.getReferenceById(pageId);

        PageBuildingBlock buildingBlock = new PageBuildingBlock(page, id);
        pageBuildingBlocks.save(buildingBlock);

        return buildingBlock.id();
    }

    public void removeBuildingBlockFromPage(PageBuildingBlockId buildingBlockId) {
        pageBuildingBlocks.deleteById(buildingBlockId);
    }

    public Collection<PageBuildingBlock> pageBuildingBlocks(PageId pageId) {
        Page page = pages.getReferenceById(pageId);

        return pageBuildingBlocks.findAllByPage(page);
    }

    public Collection<PageBuildingBlock> pageBuildingBlocks(Set<PageId> pageIds) {
        return pageBuildingBlocks.findAllByPageIdIn(pageIds);
    }

    public Optional<PageBuildingBlock> pageBuildingBlock(PageBuildingBlockId pageBuildingBlockId) {
        return pageBuildingBlocks.findById(pageBuildingBlockId);
    }

    public void configureProperty(PageBuildingBlockPropertyId propertyId, String value) {
        PageBuildingBlockPropertyValue propertyValue = pageBuildingBlockPropertyValues.findById(propertyId)
                .orElse(new PageBuildingBlockPropertyValue(propertyId));

        propertyValue.set(value);

        pageBuildingBlockPropertyValues.save(propertyValue);
    }

    public Collection<PageBuildingBlockPropertyValue> buildingBlockPropertyValues(PageBuildingBlockId pageBuildingBlockId) {
        return pageBuildingBlockPropertyValues.findAllByPageBuildingBlockId(pageBuildingBlockId);
    }
}
