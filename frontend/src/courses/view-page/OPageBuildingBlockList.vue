<template>
  <OBaseAnimatedList
    :key-fn="keyFn"
    :items="buildingBlocks"
    :initialized="initialized"
    :fetching="fetching"
    empty-message="Keine Bausteine hinzugefügt">
    <template #item="{ item }">
      <slot name="item" :pageBuildingBlock="item" />
    </template>
  </OBaseAnimatedList>
</template>

<script lang="ts" setup>
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import { PageBuildingBlock } from 'src/services/generated/openapi/courses';

defineProps<{
  buildingBlocks: PageBuildingBlock[];
  initialized?: boolean;
  fetching?: boolean;
}>();

function keyFn(buildingBlock: PageBuildingBlock) {
  return buildingBlock.pageBuildingBlockId;
}
</script>
