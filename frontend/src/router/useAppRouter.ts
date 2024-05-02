import { RouteLocationRaw, useRouter } from 'vue-router';
import { availableRoutes } from 'src/router/routes';
import { BuildingBlockVersion } from 'src/services/generated/openapi';

export function useAppRouter() {
  const router = useRouter();

  function goToRoute(route: RouteLocationRaw, replace = false) {
    if (replace) {
      return router.replace(route);
    }

    return router.push(route);
  }

  function browseLessons() {
    return goToRoute({
      name: availableRoutes.browse_lessons,
    });
  }

  function viewLessonRoute(lessonId: string) {
    return {
      name: availableRoutes.view_lesson,
      params: {
        lessonId,
      },
    };
  }

  function viewLesson(lessonId: string) {
    return goToRoute(viewLessonRoute(lessonId));
  }

  function viewTeachingUnitRoute(lessonId: string, teachingUnitId: string) {
    return {
      name: availableRoutes.view_teaching_unit,
      params: {
        lessonId,
        teachingUnitId,
      },
    };
  }

  function viewCourse(lessonId: string, courseId: string) {
    return goToRoute({
      name: availableRoutes.view_course,
      params: {
        lessonId,
        courseId,
      },
    });
  }

  function viewBuildingBlockRoute(buildingBlockVersion: BuildingBlockVersion) {
    return {
      name: availableRoutes.view_building_block,
      params: {
        buildingBlockId: buildingBlockVersion.buildingBlockId,
        version: buildingBlockVersion.version,
      },
    };
  }

  function viewBuildingBlock(buildingBlockVersion: BuildingBlockVersion) {
    return goToRoute(viewBuildingBlockRoute(buildingBlockVersion));
  }

  function viewPageRoute(courseId: string, pageId: string) {
    return {
      name: availableRoutes.view_page,
      params: {
        courseId,
        pageId,
      },
    };
  }

  function viewPage(courseId: string, pageId: string) {
    return goToRoute(viewPageRoute(courseId, pageId));
  }

  function selectBuildingBlockRoute(courseId: string, pageId: string) {
    return {
      name: availableRoutes.select_building_block,
      params: {
        courseId,
        pageId,
      },
    };
  }

  return {
    viewPage,
    viewCourse,
    viewLesson,
    viewPageRoute,
    browseLessons,
    viewLessonRoute,
    viewBuildingBlock,
    viewTeachingUnitRoute,
    viewBuildingBlockRoute,
    selectBuildingBlockRoute,
  };
}
