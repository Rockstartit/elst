<template>
  <PBase content-width="600px">
    <OBuildingBlockList>
      <template #item="{ buildingBlock }">
        <MBuildingBlockOverview
          :building-block="buildingBlock"
          clickable
          @click="viewBuildingBlock(buildingBlock.version)" />
      </template>
    </OBuildingBlockList>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import OBuildingBlockList from 'src/building-blocks/browse/OBuildingBlockList.vue';
import MBuildingBlockOverview from 'src/building-blocks/browse/MBuildingBlockOverview.vue';
import { useAppRouter } from 'src/router/useAppRouter';

const { viewBuildingBlock } = useAppRouter();
</script>
