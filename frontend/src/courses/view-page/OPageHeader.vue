<template>
  <div class="col">
    <div class="row text-h6">
      {{ title }}
    </div>

    <q-separator class="q-mt-sm" />
  </div>
</template>

<script lang="ts" setup>
defineProps<{
  title: string;
}>();
</script>
