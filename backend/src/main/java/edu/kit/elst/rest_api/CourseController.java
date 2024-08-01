package edu.kit.elst.rest_api;

import edu.kit.elst.building_blocks.BuildingBlock;
import edu.kit.elst.building_blocks.BuildingBlockAppService;
import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.core.shared.*;
import edu.kit.elst.course_planning.*;
import edu.kit.elst.course_planning.PageLink;
import edu.kit.elst.course_planning.PageMockup;
import edu.kit.elst.course_planning.Page;
import edu.kit.elst.course_planning.PageBuildingBlock;
import edu.kit.elst.lesson_planning.*;
import edu.kit.elst.lesson_planning.LearningMaterial;
import edu.kit.elst.lesson_planning.TeachingPhase;
import edu.kit.elst.lesson_planning.TeachingUnit;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CourseController implements CourseApi {
    private final PageAppService pageAppService;
    private final CourseAppService courseAppService;
    private final PageMockupAppService pageMockupAppService;
    private final BuildingBlockAppService buildingBlockService;
    private final TeachingUnitAppService teachingUnitAppService;
    private final TeachingPhaseAppService teachingPhaseAppService;
    private final LearningMaterialAppService learningMaterialAppService;
    private final PageBuildingBlockAppService pageBuildingBlockAppService;

    @Override
    public ResponseEntity<UUID> createCourse(UUID lessonId, CreateCourseRequest body) {
        LessonId aLessonId = new LessonId(lessonId);
        TechnologyWish technologyWish = new TechnologyWish(body.getTechnologyWish());

        CourseId courseId = courseAppService.createCourse(aLessonId, technologyWish);

        return ResponseEntity.ok(courseId.value());
    }

    @Override
    public ResponseEntity<Void> editCourse(UUID courseId, EditCourseRequest body) {
        CourseId aCourseId = new CourseId(courseId);

        if (body.getNotes() != null) {
            courseAppService.editCourseNotes(aCourseId, body.getNotes());
        }

        if (body.getTechnologyWish() != null) {
            TechnologyWish technologyWish = new TechnologyWish(body.getTechnologyWish());

            courseAppService.editTechnologyWish(aCourseId, technologyWish);
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteCourse(UUID courseId) {
        CourseId aCourseId = new CourseId(courseId);

        courseAppService.deleteCourse(aCourseId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<CourseOverview>> getAllCourses(UUID lessonId) {
        Collection<edu.kit.elst.course_planning.Course> courses;

        if (lessonId != null) {
            LessonId aLessonId = new LessonId(lessonId);

            courses = courseAppService.courses(aLessonId);
        } else {
            courses = courseAppService.courses();
        }

        return ResponseEntity.ok(courses.stream()
                .map(CourseMapper::mapToCourseOverview)
                .toList());
    }

    @Override
    public ResponseEntity<Course> getCourse(UUID courseId) {
        CourseId aCourseId = new CourseId(courseId);

        edu.kit.elst.course_planning.Course course = courseAppService.course(aCourseId)
                .orElseThrow(() -> new CourseNotFoundException(aCourseId));

        CourseNote notes = courseAppService.courseNote(aCourseId);

        return ResponseEntity.ok(CourseMapper.mapToCourse(course, notes));
    }

    @Override
    public ResponseEntity<List<CourseTeachingUnit>> getCourseStructure(UUID courseId) {
        CourseId aCourseId = new CourseId(courseId);

        edu.kit.elst.course_planning.Course course = courseAppService.course(aCourseId)
                .orElseThrow(() -> new CourseNotFoundException(aCourseId));

        Collection<TeachingUnit> teachingUnits = teachingUnitAppService.teachingUnits(course.lessonId());
        Set<TeachingUnitId> teachingUnitIds = teachingUnits.stream().map(TeachingUnit::id).collect(Collectors.toSet());

        Map<TeachingUnitId, List<TeachingPhase>> teachingPhases
                = teachingPhaseAppService.teachingPhases(teachingUnitIds).stream()
                .collect(Collectors.groupingBy(TeachingPhase::teachingUnitId));
        Set<TeachingPhaseId> teachingPhaseIds = teachingPhases.values().stream()
                .flatMap(Collection::stream)
                .map(TeachingPhase::id)
                .collect(Collectors.toSet());

        Map<TeachingPhaseId, List<LearningMaterial>> learningMaterialsMap
                = learningMaterialAppService.learningMaterials(teachingPhaseIds).stream()
                .collect(Collectors.groupingBy(LearningMaterial::teachingPhaseId));

        Map<TeachingPhaseId, List<Page>> pagesMap = pageAppService.pages(aCourseId, teachingPhaseIds).stream()
                .collect(Collectors.groupingBy(Page::teachingPhaseId));
        Set<PageId> pageIds = pagesMap.values().stream()
                .flatMap(Collection::stream)
                .map(Page::id)
                .collect(Collectors.toSet());

        Map<PageId, List<PageMockup>> mockupsMap = pageMockupAppService.mockupsByPageId(pageIds).stream()
                .collect(Collectors.groupingBy(PageMockup::pageId));

        Map<PageId, List<PageBuildingBlock>> pageBuildingBlocksMap = pageBuildingBlockAppService.pageBuildingBlocks(pageIds).stream()
                .collect(Collectors.groupingBy(PageBuildingBlock::pageId));
        Set<BuildingBlockId> buildingBlockIds = pageBuildingBlocksMap.values().stream()
                .flatMap(Collection::stream)
                .map(PageBuildingBlock::buildingBlockId)
                .collect(Collectors.toSet());

        Map<BuildingBlockId, BuildingBlock> buildingBlockMap = buildingBlockService.buildingBlocks(buildingBlockIds).stream()
                .collect(Collectors.toMap(BuildingBlock::id, Function.identity()));

        Map<PageId, Collection<PageLink>> pageLinksMap = new HashMap<>();
        for (PageId pageId : pageIds) {
            pageLinksMap.put(pageId, pageAppService.pageLinks(pageId));
        }

        Set<PageId> linkedPageIds = pageLinksMap.values().stream()
                .flatMap(Collection::stream)
                .map(PageLink::targetPageId)
                .collect(Collectors.toSet());

        Map<PageId, Page> linkedPagesMap = pageAppService.pages(linkedPageIds).stream()
                .collect(Collectors.toMap(Page::id, Function.identity()));

        return ResponseEntity.ok(teachingUnits.stream()
                .sorted(Comparator.comparing(TeachingUnit::order))
                .map(teachingUnit -> CourseMapper.mapToCourseTeachingUnit(
                        teachingUnit,
                        teachingPhases.getOrDefault(teachingUnit.id(), Collections.emptyList()),
                        learningMaterialsMap,
                        pagesMap,
                        mockupsMap,
                        pageBuildingBlocksMap,
                        pageLinksMap,
                        linkedPagesMap,
                        buildingBlockMap
                ))
                .toList());
    }
}
