import { ref } from 'vue';
import { BuildingBlock } from 'src/services/generated/openapi/building_blocks';
import { withLoading } from 'src/core/useWithLoading';
import { buildingBlockApi } from 'src/services';

export function useBrowseBuildingBlocks() {
  const buildingBlocks = ref<BuildingBlock[]>([]);
  const fetching = ref(false);

  function fetchBuildingBlocks() {
    return withLoading(
      buildingBlockApi.getBuildingBlocks().then((response) => {
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
