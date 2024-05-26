import { RouteLocationRaw, useRouter } from 'vue-router';
import { availableRoutes } from 'src/router/routes';

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

  function viewBuildingBlockRoute(buildingBlockId: string) {
    return {
      name: availableRoutes.view_building_block,
      params: {
        buildingBlockId,
      },
    };
  }

  function viewBuildingBlock(buildingBlockId: string) {
    return goToRoute(viewBuildingBlockRoute(buildingBlockId));
  }

  return {
    viewCourse,
    viewLesson,
    browseLessons,
    viewLessonRoute,
    viewBuildingBlock,
    viewTeachingUnitRoute,
    viewBuildingBlockRoute,
  };
}
