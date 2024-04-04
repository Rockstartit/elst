<template>
  <div class="col">
    <div class="row">
      <MHoverable
        v-ripple
        class="relative-position"
        @click="$emit('edit-name')">
        <div class="row text-h6 items-center" style="gap: 0.5rem">
          <span>
            {{ title ?? 'Neue Seite' }}
          </span>
          <q-icon name="mdi-pencil-outline" />
        </div>
      </MHoverable>
    </div>

    <q-separator class="q-mt-sm" />
  </div>
</template>

<script lang="ts" setup>
import MHoverable from "src/core/MHoverable.vue";

defineProps<{
  title: string;
}>();

defineEmits(['edit-name']);
</script>
