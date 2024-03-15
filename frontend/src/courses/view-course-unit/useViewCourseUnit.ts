import { ref } from 'vue';
import { PageOverview } from 'src/services/generated/openapi/courses';
import { withLoading } from 'src/core/useWithLoading';
import { pageApi } from 'src/services';

export function useViewCourseUnit() {
  const pages = ref<PageOverview[]>([]);
  const fetching = ref(false);

  function fetchPages(courseUnitId: string) {
    return withLoading(
      pageApi.getPages(courseUnitId).then((response) => {
        pages.value = response.data;
      }),
      fetching
    );
  }

  return {
    pages,
    fetching,
    fetchPages,
  };
}
