package edu.kit.elst.course_planning;

import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.core.shared.PageBuildingBlockId;
import edu.kit.elst.core.shared.PageId;
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
public class PageBuildingBlockAppService {
    private final Pages pages;
    private final PageBuildingBlocks pageBuildingBlocks;
    private final PageBuildingBlockPropertyValues pageBuildingBlockPropertyValues;

    public PageBuildingBlockId addBuildingBlockToPage(PageId pageId, BuildingBlockId id) {
        Page page = pages.getReferenceById(pageId);
        Long maxOrder = pageBuildingBlocks.maxOrderByPage(page);
        long order = maxOrder != null ? maxOrder + 1 : 0;

        PageBuildingBlock buildingBlock = new PageBuildingBlock(page, id);
        buildingBlock.order(order);
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

    public void reorderPageBuildingBlocks(PageId pageId, List<PageBuildingBlockId> pageBuildingBlockIds) {
        Page page = pages.getReferenceById(pageId);

        Collection<PageBuildingBlock> buildingBlocks = pageBuildingBlocks.findAllByPage(page);

        for (PageBuildingBlock pageBuildingBlock : buildingBlocks) {
            pageBuildingBlock.order(pageBuildingBlockIds.indexOf(pageBuildingBlock.id()));
        }
    }
}
