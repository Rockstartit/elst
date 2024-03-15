import { ref } from 'vue';
import { withLoading } from 'src/core/useWithLoading';
import { courseApi } from 'src/services';
import { Course } from 'src/services/generated/openapi/courses';

export function useBrowseCourses() {
  const courses = ref<Course[]>([]);
  const fetching = ref(false);

  function fetchCourses() {
    return withLoading(
      courseApi.getAllCourses().then((response) => {
        courses.value = response.data;
      }),
      fetching
    );
  }

  return {
    courses,
    fetching,
    fetchCourses,
  };
}
