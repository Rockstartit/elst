package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlock;
import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.communication.BuildingBlockReference;
import edu.kit.elst.communication.Comment;
import edu.kit.elst.communication.ReferencesToDiscussion;
import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.course_conceptualization.PageMockup;
import edu.kit.elst.course_conceptualization.Page;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiscussionMapper {
    public static DiscussionOverview mapToDiscussionOverview(edu.kit.elst.communication.Discussion discussion, edu.kit.elst.users.User createdBy) {
        DiscussionOverview dto = new DiscussionOverview();

        dto.setId(discussion.id().value());
        dto.setTitle(discussion.title());
        dto.setCreatedBy(UserMapper.mapToUserProfile(createdBy));
        dto.setState(discussion.state());

        discussion.resolvedAt().ifPresent(resolvedAt -> dto.setResolvedAt(resolvedAt.atOffset(ZoneOffset.UTC)));

        return dto;
    }

    public static Discussion mapToDiscussion(edu.kit.elst.communication.Discussion discussion,
                                             edu.kit.elst.users.User createdBy,
                                             ReferencesToDiscussion references,
                                             Map<BuildingBlockId, BuildingBlock> buildingBlockMap,
                                             Map<PageId, edu.kit.elst.course_conceptualization.Page> pageMap,
                                             Map<MockupId, PageMockup> mockupMap) {
        Discussion dto = new Discussion();

        dto.setId(discussion.id().value());
        dto.setTitle(discussion.title());
        dto.setCreatedBy(UserMapper.mapToUserProfile(createdBy));
        dto.setState(discussion.state());

        discussion.resolvedAt().ifPresent(resolvedAt -> dto.setResolvedAt(resolvedAt.atOffset(ZoneOffset.UTC)));

        List<DiscussionReference> referenceDTOs = new ArrayList<>();
        referenceDTOs.addAll(references.buildingBlockReferences().stream()
                .map(reference -> mapToBuildingBlockReference(reference, buildingBlockMap.get(reference.buildingBlockId())))
                .toList());
        referenceDTOs.addAll(references.pageReferences().stream()
                .map(reference -> mapToPageReference(reference, pageMap.get(reference.pageId())))
                .toList());
        referenceDTOs.addAll(references.mockupReferences().stream()
                .map(reference -> mapToMockupReference(reference, mockupMap.get(reference.mockupId())))
                .toList());

        dto.setReferences(referenceDTOs);

        return dto;
    }

    private static edu.kit.elst.rest_api.MockupReference mapToMockupReference(edu.kit.elst.communication.MockupReference reference, PageMockup mockup) {
        edu.kit.elst.rest_api.MockupReference dto = new edu.kit.elst.rest_api.MockupReference();

        dto.setMockupId(reference.mockupId().value());
        dto.setDescription(mockup.description());

        return dto;
    }

    private static edu.kit.elst.rest_api.PageReference mapToPageReference(edu.kit.elst.communication.PageReference reference, Page page) {
        edu.kit.elst.rest_api.PageReference dto = new edu.kit.elst.rest_api.PageReference();

        dto.setPageId(reference.pageId().value());
        dto.setTitle(page.title());

        return dto;
    }

    private static edu.kit.elst.rest_api.BuildingBlockReference mapToBuildingBlockReference(BuildingBlockReference reference, BuildingBlock buildingBlock) {
        edu.kit.elst.rest_api.BuildingBlockReference dto = new edu.kit.elst.rest_api.BuildingBlockReference();

        dto.setBuildingBlockId(reference.buildingBlockId().value());
        dto.setName(buildingBlock.details().name());

        return dto;
    }

    public static edu.kit.elst.rest_api.Comment mapToComment(Comment comment, edu.kit.elst.users.User createdBy) {
        edu.kit.elst.rest_api.Comment dto = new edu.kit.elst.rest_api.Comment();

        dto.setId(comment.id().value());
        dto.setCreatedBy(UserMapper.mapToUserProfile(createdBy));
        dto.setCreatedAt(comment.createdAt().atOffset(ZoneOffset.UTC));
        dto.setContent(comment.content());

        return dto;
    }
}
