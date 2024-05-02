<template>
  <div>
    {{ pageBuildingBlock }}
  </div>
</template>

<script setup lang="ts">
import { PageBuildingBlock } from 'src/services/generated/openapi';

defineProps<{
  pageBuildingBlock: PageBuildingBlock;
}>();
</script>
