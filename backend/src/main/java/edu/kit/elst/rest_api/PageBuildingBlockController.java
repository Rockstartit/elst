package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlock;
import edu.kit.elst.building_blocks.BuildingBlockAppService;
import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.core.shared.PageBuildingBlockId;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.course_conceptualization.PageBuildingBlock;
import edu.kit.elst.course_conceptualization.PageBuildingBlockAppService;
import edu.kit.elst.course_conceptualization.PageBuildingBlockPropertyId;
import edu.kit.elst.course_conceptualization.PageBuildingBlockPropertyValue;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class PageBuildingBlockController implements PageBuildingBlockApi {
    private final BuildingBlockAppService buildingBlockService;
    private final PageBuildingBlockAppService pageBuildingBlockAppService;

    @Override
    public ResponseEntity<UUID> addBuildingBlockToPage(UUID pageId, AddBuildingBlockToPageRequest body) {
        PageId aPageId = new PageId(pageId);
        BuildingBlockId buildingBlockId = new BuildingBlockId(body.getBuildingBlockId());

        PageBuildingBlockId pageBuildingBlockId = pageBuildingBlockAppService.addBuildingBlockToPage(aPageId, buildingBlockId);

        return ResponseEntity.ok(pageBuildingBlockId.value());
    }

    @Override
    public ResponseEntity<UUID> removeBuildingBlockFromPage(UUID pageBuildingBlockId) {
        PageBuildingBlockId aPageBuildingBlockId = new PageBuildingBlockId(pageBuildingBlockId);

        pageBuildingBlockAppService.removeBuildingBlockFromPage(aPageBuildingBlockId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<PageBuildingBlockProperty>> getBuildingBlockProperties(UUID pageBuildingBlockId) {
        PageBuildingBlockId aPageBuildingBlockId = new PageBuildingBlockId(pageBuildingBlockId);

        PageBuildingBlock pageBuildingBlock = pageBuildingBlockAppService.pageBuildingBlock(aPageBuildingBlockId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BuildingBlock buildingBlock = buildingBlockService.buildingBlock(pageBuildingBlock.buildingBlockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Map<String, PageBuildingBlockPropertyValue> propertyValueMap
                = pageBuildingBlockAppService.buildingBlockPropertyValues(aPageBuildingBlockId).stream()
                .collect(Collectors.toMap(PageBuildingBlockPropertyValue::key, Function.identity()));

        return ResponseEntity.ok(buildingBlock.properties().stream()
                .map(property -> CourseMapper.mapToPageBuildingBlockProperty(property, propertyValueMap.get(property.key())))
                .toList());
    }

    @Override
    public ResponseEntity<Void> editBuildingBlockProperty(UUID pageBuildingBlockId, String key, EditBuildingBlockPropertyValueRequest body) {
        PageBuildingBlockId aPageBuildingBlockId = new PageBuildingBlockId(pageBuildingBlockId);
        PageBuildingBlockPropertyId propertyId = new PageBuildingBlockPropertyId(aPageBuildingBlockId, key);

        pageBuildingBlockAppService.configureProperty(propertyId, body.getValue());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> reorderPageBuildingBlocks(UUID pageId, List<UUID> pageBuildingBlockIds) {
        PageId aPageId = new PageId(pageId);
        List<PageBuildingBlockId> thePageBuildingBlockIds = pageBuildingBlockIds.stream()
                .map(PageBuildingBlockId::new)
                .toList();

        pageBuildingBlockAppService.reorderPageBuildingBlocks(aPageId, thePageBuildingBlockIds);

        return ResponseEntity.ok().build();
    }
}
