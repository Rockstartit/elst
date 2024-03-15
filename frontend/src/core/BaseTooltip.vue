<template>
  <q-tooltip class="text-caption" :max-width="width">
    <slot />
  </q-tooltip>
</template>

<script lang="ts" setup>
defineProps<{
  width?: string;
}>();
</script>
