<template>
  <div>
    {{ mockup }}
  </div>
</template>

<script setup lang="ts">
import { Mockup } from 'src/services/generated/openapi';

defineProps<{
  mockup: Mockup;
}>();
</script>
