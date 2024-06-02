<template>
  <div class="row">
    <MHoverable v-ripple class="relative-position" @click="$emit('edit-title')">
      <div class="row text-h6 items-center" style="gap: 0.5rem">
        <span>
          {{ title }}
        </span>
        <q-icon name="mdi-pencil-outline" />
      </div>
    </MHoverable>
  </div>
</template>

<script lang="ts" setup>
import MHoverable from 'src/core/MHoverable.vue';

defineProps<{
  title: string;
}>();

defineEmits<{
  (e: 'edit-title'): void;
}>();
</script>
