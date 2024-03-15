import { RouteRecordRaw } from 'vue-router';

export const availableRoutes = {
  browse_courses: 'browse_courses',
  view_course: 'view_course',
  browse_building_blocks: 'browse_building_blocks',
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
        component: () => import('src/courses/browse/PBrowseCourses.vue'),
      },
      {
        name: availableRoutes.view_course,
        path: 'courses/:courseId/:version',
        props: true,
        component: () => import('src/courses/view/PViewCourse.vue'),
      },
      {
        name: availableRoutes.browse_building_blocks,
        path: 'building-blocks/browse',
        component: () =>
          import('src/building_blocks/browse/PBrowseBuildingBlocks.vue'),
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
