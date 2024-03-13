<template>
  <transition enter-active-class="animated fadeIn">
    <div v-if="initialized && fetching" class="row justify-center q-py-md">
      <q-spinner />
    </div>

    <q-list v-else-if="initialized && buildingBlocks.length > 0">
      <MBuildingBlockOverview
        v-for="buildingBlock in buildingBlocks"
        :key="buildingBlock.id + buildingBlock.version"
        :building-block="buildingBlock"
        clickable />
    </q-list>

    <div v-else-if="initialized"> Keine Bausteine verfügbar </div>
  </transition>
</template>

<script lang="ts" setup>
import MBuildingBlockOverview from 'src/building_blocks/browse/MBuildingBlockOverview.vue';
import { useBrowseBuildingBlocks } from 'src/building_blocks/browse/useBrowseBuildingBlocks';
import { onMounted, ref } from 'vue';

const { fetching, buildingBlocks, fetchBuildingBlocks } =
  useBrowseBuildingBlocks();

const initialized = ref(false);

onMounted(() => {
  fetchBuildingBlocks().finally(() => {
    initialized.value = true;
  });
});
</script>
