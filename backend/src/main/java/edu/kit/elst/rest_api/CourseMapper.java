package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlock;
import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.building_blocks.BuildingBlockProperty;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.core.shared.TeachingPhaseId;
import edu.kit.elst.course_conceptualization.CourseNote;
import edu.kit.elst.course_conceptualization.PageBuildingBlockPropertyValue;
import edu.kit.elst.course_conceptualization.PageMockup;
import edu.kit.elst.lesson_planning.TeachingPhase;
import edu.kit.elst.lesson_planning.TeachingUnit;

import java.util.*;

public class CourseMapper {
    public static Course mapToCourse(edu.kit.elst.course_conceptualization.Course course, CourseNote notes) {
        Course dto = new Course();

        dto.setId(course.id().value());
        dto.setLessonId(course.lessonId().value());
        dto.setTechnologyWish(course.technologyWish().value());
        dto.setNotes(notes.content());

        return dto;
    }

    public static CourseOverview mapToCourseOverview(edu.kit.elst.course_conceptualization.Course course) {
        CourseOverview dto = new CourseOverview();

        dto.setId(course.id().value());
        dto.setLessonId(course.lessonId().value());
        dto.setTechnologyWish(course.technologyWish().value());

        return dto;
    }

    public static Mockup mapToMockup(PageMockup mockup) {
        Mockup dto = new Mockup();

        dto.setId(mockup.id().value());
        dto.setFileId(mockup.fileId().value());
        dto.setDescription(mockup.description());

        return dto;
    }

    public static LearningMaterial mapToLearningMaterial(edu.kit.elst.lesson_planning.LearningMaterial learningMaterial) {
        LearningMaterial dto = new LearningMaterial();

        dto.setId(learningMaterial.id().value());
        dto.setName(learningMaterial.name());
        dto.setFileId(learningMaterial.fileId().value());

        return dto;
    }

    public static Page mapToPage(edu.kit.elst.course_conceptualization.Page page,
                                 Collection<edu.kit.elst.course_conceptualization.Page> linkedPages,
                                 Collection<PageMockup> mockups,
                                 Collection<edu.kit.elst.course_conceptualization.PageBuildingBlock> pageBuildingBlocks,
                                 Map<BuildingBlockId, edu.kit.elst.building_blocks.BuildingBlock> buildingBlockMap) {
        Page dto = new Page();

        dto.setId(page.id().value());
        dto.setTitle(page.title());
        dto.setOrder(UtilMapper.mapToBigDecimal(page.order()));
        dto.setTeachingPhaseId(page.teachingPhaseId().value());
        dto.setMockups(mockups.stream()
                .map(CourseMapper::mapToMockup)
                .toList());
        dto.setLinkedPages(linkedPages.stream()
                .map(CourseMapper::mapToPageOverview)
                .toList());
        dto.setBuildingBlocks(pageBuildingBlocks.stream()
                .map(pageBuildingBlock -> mapToPageBuildingBlock(
                        pageBuildingBlock, buildingBlockMap.get(pageBuildingBlock.buildingBlockId())))
                .toList());

        return dto;
    }

    public static edu.kit.elst.rest_api.PageBuildingBlock mapToPageBuildingBlock(edu.kit.elst.course_conceptualization.PageBuildingBlock pageBuildingBlock, BuildingBlock buildingBlock) {
        edu.kit.elst.rest_api.PageBuildingBlock dto = new edu.kit.elst.rest_api.PageBuildingBlock();

        dto.setPageBuildingBlockId(pageBuildingBlock.id().value());
        dto.setBuildingBlockId(pageBuildingBlock.buildingBlockId().value());
        dto.setName(buildingBlock.details().name());
        dto.setDescription(buildingBlock.details().description());
        dto.setReleaseStatus(buildingBlock.releaseStatus());

        return dto;
    }

    public static PageBuildingBlockProperty mapToPageBuildingBlockProperty(BuildingBlockProperty property, PageBuildingBlockPropertyValue propertyValue) {
        PageBuildingBlockProperty dto = new PageBuildingBlockProperty();

        dto.setKey(property.key());
        dto.setDisplayName(property.displayName());
        dto.setDescription(property.description());
        dto.setOrder(UtilMapper.mapToBigDecimal(property.order()));
        dto.setType(property.type());

        if (propertyValue != null) {
            dto.setValue(propertyValue.value());
        } else {
            dto.setValue("");
        }

        return dto;
    }

    public static PageOverview mapToPageOverview(edu.kit.elst.course_conceptualization.Page page) {
        PageOverview dto = new PageOverview();

        dto.setId(page.id().value());
        dto.setTitle(page.title());
        dto.setTeachingPhaseId(page.teachingPhaseId().value());
        dto.setOrder(UtilMapper.mapToBigDecimal(page.order()));

        return dto;
    }

    public static CourseTeachingUnit mapToCourseTeachingUnit(TeachingUnit teachingUnit,
                                                             List<edu.kit.elst.lesson_planning.TeachingPhase> teachingPhases,
                                                             Map<TeachingPhaseId, List<edu.kit.elst.lesson_planning.LearningMaterial>> learningMaterialsMap,
                                                             Map<TeachingPhaseId, List<edu.kit.elst.course_conceptualization.Page>> pagesMap,
                                                             Map<PageId, List<PageMockup>> mockupsMap,
                                                             Map<PageId, List<edu.kit.elst.course_conceptualization.PageBuildingBlock>> pageBuildingBlocksMap,
                                                             Map<PageId, Collection<edu.kit.elst.course_conceptualization.Page>> linkedPagesMap,
                                                             Map<BuildingBlockId, BuildingBlock> buildingBlockMap) {
        CourseTeachingUnit dto = new CourseTeachingUnit();

        dto.setId(teachingUnit.id().value());
        dto.setTopic(teachingUnit.topic().value());
        dto.setTeachingPhases(teachingPhases.stream()
                .sorted(Comparator.comparing(TeachingPhase::order))
                .map(teachingPhase -> mapToCourseTeachingPhase(
                        teachingPhase,
                        learningMaterialsMap.getOrDefault(teachingPhase.id(), Collections.emptyList()),
                        pagesMap.getOrDefault(teachingPhase.id(), Collections.emptyList()),
                        mockupsMap,
                        pageBuildingBlocksMap,
                        linkedPagesMap,
                        buildingBlockMap
                ))
                .toList());

        return dto;
    }

    public static CourseTeachingPhase mapToCourseTeachingPhase(TeachingPhase teachingPhase,
                                                               List<edu.kit.elst.lesson_planning.LearningMaterial> learningMaterials,
                                                               List<edu.kit.elst.course_conceptualization.Page> pages,
                                                               Map<PageId, List<PageMockup>> mockupsMap,
                                                               Map<PageId, List<edu.kit.elst.course_conceptualization.PageBuildingBlock>> pageBuildingBlocksMap,
                                                               Map<PageId, Collection<edu.kit.elst.course_conceptualization.Page>> linkedPagesMap,
                                                               Map<BuildingBlockId, BuildingBlock> buildingBlockMap) {
        CourseTeachingPhase dto = new CourseTeachingPhase();

        dto.setId(teachingPhase.id().value());
        dto.setTopic(teachingPhase.topic().value());

        teachingPhase.phase().ifPresent(dto::setPhase);
        teachingPhase.timeFrame().map(UtilMapper::mapToBigDecimal).ifPresent(dto::setTimeFrame);

        dto.setLearningMaterials(learningMaterials.stream()
                .map(CourseMapper::mapToLearningMaterial)
                .toList());

        dto.setPages(pages.stream()
                .sorted(Comparator.comparing(edu.kit.elst.course_conceptualization.Page::order))
                .map(page -> CourseMapper.mapToPage(
                        page,
                        linkedPagesMap.getOrDefault(page.id(), Collections.emptyList()),
                        mockupsMap.getOrDefault(page.id(), Collections.emptyList()),
                        pageBuildingBlocksMap.getOrDefault(page.id(), Collections.emptyList()),
                        buildingBlockMap
                ))
                .toList());

        return dto;
    }
}
