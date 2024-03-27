<template>
  <PBase content-width="1200px">
    Baustein anzeigen {{ buildingBlockVersion }}
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { computed } from 'vue';
import { BuildingBlockVersion } from 'src/services/generated/openapi/building_blocks';

const props = defineProps<{
  buildingBlockId: string;
  version: string;
}>();

const buildingBlockVersion = computed<BuildingBlockVersion>(() => {
  return {
    buildingBlockId: props.buildingBlockId,
    version: Number.parseInt(props.version),
  };
});
</script>
