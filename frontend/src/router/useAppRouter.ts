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

  function viewLesson(lessonId: string) {
    return goToRoute({
      name: availableRoutes.view_lesson,
      params: {
        lessonId,
      },
    });
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

  return {
    viewLesson,
    browseLessons,
    viewBuildingBlock,
    viewTeachingUnitRoute,
    viewBuildingBlockRoute,
  };
}
