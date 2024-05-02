<template>
  <div>
    {{ page }}
  </div>
</template>

<script setup lang="ts">
import { PageOverview } from 'src/services/generated/openapi';

defineProps<{
  page: PageOverview;
}>();
</script>
