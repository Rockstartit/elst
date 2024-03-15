import { ref } from 'vue';
import {
  CourseUnit,
  CourseVersion,
} from 'src/services/generated/openapi/courses';
import { withLoading } from 'src/core/useWithLoading';
import { courseUnitApi } from 'src/services';

export function useCourseUnits() {
  const courseUnits = ref<CourseUnit[]>([]);
  const fetching = ref(false);

  function fetchCourseUnits(courseVersion: CourseVersion) {
    return withLoading(
      courseUnitApi
        .getAllCourseUnits(courseVersion.courseId, courseVersion.version)
        .then((response) => {
          courseUnits.value = response.data;
        }),
      fetching
    );
  }

  return {
    fetching,
    courseUnits,
    fetchCourseUnits,
  };
}
