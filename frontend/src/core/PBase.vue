<template>
  <q-page class="column items-center text-grey-10">
    <div class="q-py-md" :style="{ width: contentWidth }">
      <slot />
    </div>
  </q-page>
</template>

<script lang="ts" setup>
withDefaults(
  defineProps<{
    contentWidth?: string;
  }>(),
  {
    contentWidth: '100%',
  }
);
</script>
