import { RouteRecordRaw } from 'vue-router';

export const availableRoutes = {
  browse_courses: 'browse_courses',
  view_course: 'view_course',
  view_course_unit: 'view_course_unit',
  view_page: 'view_page',
  browse_building_blocks: 'browse_building_blocks',
  select_building_block: 'select_building_block',
  view_building_block: 'view_building_block',
};

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    redirect: { name: availableRoutes.browse_courses },
    children: [
      {
        name: availableRoutes.browse_courses,
        path: 'courses/browse',
        props: true,
        component: () => import('src/courses/browse/PBrowseCourses.vue'),
      },
      {
        name: availableRoutes.view_course,
        path: 'courses/:courseId/:version',
        props: true,
        component: () => import('src/courses/view-course/PViewCourse.vue'),
      },
      {
        name: availableRoutes.view_course_unit,
        path: 'courses/:courseId/:version/units/:courseUnitId',
        props: true,
        component: () =>
          import('src/courses/view-course-unit/PViewCourseUnit.vue'),
      },
      {
        name: availableRoutes.view_page,
        path: 'courses/:courseId/:version/units/:courseUnitId/pages/:pageId',
        props: true,
        component: () => import('src/courses/view-page/PViewPage.vue'),
      },
      {
        name: availableRoutes.select_building_block,
        path: 'courses/:courseId/:version/units/:courseUnitId/pages/:pageId/select',
        props: true,
        component: () =>
          import('src/courses/select-building-block/PSelectBuildingBlock.vue'),
      },
      {
        name: availableRoutes.browse_building_blocks,
        path: 'building-blocks/browse',
        props: true,
        component: () =>
          import('src/building-blocks/browse/PBrowseBuildingBlocks.vue'),
      },
      {
        name: availableRoutes.view_building_block,
        path: 'building-blocks/:buildingBlockId/:version',
        props: true,
        component: () =>
          import(
            'src/building-blocks/view-building-block/PViewBuildingBlock.vue'
          ),
      },
    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    redirect: { name: availableRoutes.browse_courses },
  },
];

export default routes;
