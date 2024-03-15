import { useRouter } from 'vue-router';
import { availableRoutes } from 'src/router/routes';
import { CourseVersion } from 'src/services/generated/openapi/courses';

export function useAppRouter() {
  const router = useRouter();

  function viewCourse(courseVersion: CourseVersion) {
    return router.push({
      name: availableRoutes.view_course,
      params: {
        courseId: courseVersion.courseId,
        version: courseVersion.version,
      },
    });
  }

  return {
    viewCourse,
  };
}
