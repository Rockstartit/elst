<template>
  <OBaseAnimatedList
    :items="buildingBlocks"
    :initialized="initialized"
    :fetching="fetching"
    empty-message="Keine Bausteine verfügbar">
    <template #before>
      <slot name="before" />
    </template>
    <template #item="{ item }">
      <slot name="item" :buildingBlock="item" />
    </template>
    <template #after>
      <slot name="after" />
    </template>
  </OBaseAnimatedList>
</template>

<script lang="ts" setup>
import { useBrowseBuildingBlocks } from 'src/building-blocks/browse/useBrowseBuildingBlocks';
import { onMounted, ref } from 'vue';
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';

const { fetching, buildingBlocks, fetchBuildingBlocks } =
  useBrowseBuildingBlocks();

const initialized = ref(false);

onMounted(() => {
  fetchBuildingBlocks().finally(() => {
    initialized.value = true;
  });
});
</script>
