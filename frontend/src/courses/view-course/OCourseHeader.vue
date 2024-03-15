<template>
  <div class="col">
    <div class="row text-h6">
      {{ name }}
    </div>

    <q-separator class="q-mt-sm" />
  </div>
</template>

<script lang="ts" setup>
defineProps<{
  name: string;
}>();
</script>
