import { RouteRecordRaw } from 'vue-router';

export const availableRoutes = {
  app_base: 'app_base',
  login: 'login',
  login_error: 'login_error',
  login_callback: 'login_callback',
  browse_lessons: 'browse_lessons',
  view_lesson: 'view_lesson',
  view_teaching_unit: 'view_teaching_unit',
  view_course: 'view_course',
  view_building_block: 'view_building_block',
};

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: availableRoutes.app_base,
    component: () => import('layouts/MainLayout.vue'),
    redirect: { name: availableRoutes.browse_lessons },
    children: [
      {
        name: availableRoutes.browse_lessons,
        path: 'lessons/browse',
        props: true,
        component: () => import('src/lessons/browse/PBrowseLessons.vue'),
      },
      {
        name: availableRoutes.view_lesson,
        path: 'lessons/:lessonId',
        props: true,
        component: () => import('src/lessons/view-lesson/PViewLesson.vue'),
      },
      {
        name: availableRoutes.view_teaching_unit,
        path: 'lessons/:lessonId/teaching-units/:teachingUnitId',
        props: true,
        component: () =>
          import('src/lessons/view-teaching-unit/PViewTeachingUnit.vue'),
      },
      {
        name: availableRoutes.view_course,
        path: 'lessons/:lessonId/courses/:courseId',
        props: true,
        component: () => import('src/courses/view-course/PViewCourse.vue'),
      },
      {
        name: availableRoutes.view_building_block,
        path: 'building-blocks/:buildingBlockId',
        props: true,
        component: () =>
          import(
            'src/building-blocks/view-building-block/PViewBuildingBlock.vue'
          ),
      },
    ],
  },
  {
    path: '/pub/',
    component: () => import('layouts/PublicLayout.vue'),
    children: [
      {
        path: 'login',
        name: availableRoutes.login,
        props: true,
        component: () => import('src/public/PLogin.vue'),
      },
      {
        path: 'login-error',
        name: availableRoutes.login_error,
        props: true,
        component: () => import('src/public/PLoginError.vue'),
      },
    ],
  },
  {
    path: '/pub/',
    component: () => import('layouts/FakeMainLayout.vue'),
    children: [
      {
        path: 'callback',
        name: availableRoutes.login_callback,
        props: true,
        component: () => import('src/public/PLoginCallback.vue'),
      },
    ],
  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:pathMatch(.*)*',
    redirect: { name: availableRoutes.browse_lessons, params: {} },
  },
];

export default routes;
