<template>
  <OBuildingBlockList>
    <template #item="{ buildingBlock }">
      <MBuildingBlockOverview
        :building-block="buildingBlock"
        clickable
        @click="$emit('select', buildingBlock)" />
    </template>
  </OBuildingBlockList>
</template>

<script lang="ts" setup>
import MBuildingBlockOverview from 'src/building-blocks/browse/MBuildingBlockOverview.vue';
import OBuildingBlockList from 'src/building-blocks/browse/OBuildingBlockList.vue';
import { BuildingBlock } from 'src/services/generated/openapi';

defineEmits<{
  (e: 'select', buildingBlock: BuildingBlock): void;
}>();
</script>
