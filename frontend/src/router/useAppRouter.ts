import { RouteLocationRaw, useRouter } from 'vue-router';
import { availableRoutes } from 'src/router/routes';
import { CourseVersion } from 'src/services/generated/openapi/courses';
import { BuildingBlockVersion } from 'src/services/generated/openapi/building_blocks';

export function useAppRouter() {
  const router = useRouter();

  function goToRoute(route: RouteLocationRaw, replace = false) {
    if (replace) {
      return router.replace(route);
    }

    return router.push(route);
  }

  function viewCourse(courseVersion: CourseVersion) {
    return goToRoute({
      name: availableRoutes.view_course,
      params: {
        courseId: courseVersion.courseId,
        version: courseVersion.version,
      },
    });
  }

  function viewCourseUnit(courseVersion: CourseVersion, courseUnitId: string) {
    return goToRoute({
      name: availableRoutes.view_course_unit,
      params: {
        courseId: courseVersion.courseId,
        version: courseVersion.version,
        courseUnitId,
      },
    });
  }

  function viewPage(
    courseVersion: CourseVersion,
    courseUnitId: string,
    pageId: string,
    replace = false
  ) {
    const route = {
      name: availableRoutes.view_page,
      params: {
        courseId: courseVersion.courseId,
        version: courseVersion.version,
        courseUnitId,
        pageId,
      },
    };

    return goToRoute(route, replace);
  }

  function selectBuildingBlockRoute(
    courseVersion: CourseVersion,
    courseUnitId: string,
    pageId: string
  ) {
    return {
      name: availableRoutes.select_building_block,
      params: {
        courseId: courseVersion.courseId,
        version: courseVersion.version,
        courseUnitId,
        pageId,
      },
    };
  }

  function viewBuildingBlock(buildingBlockVersion: BuildingBlockVersion) {
    return goToRoute({
      name: availableRoutes.view_building_block,
      params: {
        buildingBlockId: buildingBlockVersion.buildingBlockId,
        version: buildingBlockVersion.version,
      },
    });
  }

  return {
    viewPage,
    viewCourse,
    viewCourseUnit,
    viewBuildingBlock,
    selectBuildingBlockRoute,
  };
}
