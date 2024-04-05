<template>
<div>
  <OMockups :building-block-version="buildingBlockVersion" />
</div>
</template>

<script setup lang="ts">

import {BuildingBlockVersion} from "src/services/generated/openapi/building_blocks";
import OMockups from "src/building-blocks/view-building-block/overview/OMockups.vue";

defineProps<{
  buildingBlockVersion: BuildingBlockVersion
}>()
</script>
