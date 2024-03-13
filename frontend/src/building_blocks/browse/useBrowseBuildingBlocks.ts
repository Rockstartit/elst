import { ref } from 'vue';
import { ReleasedBuildingBlock } from 'src/services/generated/openapi/building_blocks';
import { withLoading } from 'src/core/useWithLoading';
import { buildingBlockApi } from 'src/services';

export function useBrowseBuildingBlocks() {
  const buildingBlocks = ref<ReleasedBuildingBlock[]>([]);
  const fetching = ref(false);

  function fetchBuildingBlocks() {
    return withLoading(
      buildingBlockApi.getReleasedBuildingBlocks().then((response) => {
        buildingBlocks.value = response.data;
      }),
      fetching
    );
  }

  return {
    fetching,
    buildingBlocks,
    fetchBuildingBlocks,
  };
}
