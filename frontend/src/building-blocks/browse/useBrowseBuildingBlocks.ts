import { ref } from 'vue';
import { withLoading } from 'src/core/useWithLoading';
import { BuildingBlock } from 'src/services/generated/openapi';
import { buildingBlockApi } from 'src/services/building_blocks';

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
