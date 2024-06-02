<template>
  <MHoverable
    no-background
    no-padding
    v-slot="{ hovered }"
    class="full-width text-black">
    <q-item
      clickable
      class="elst__rounded"
      :class="{
        'text-indigo-10 bg-indigo-1 text-weight-medium': active,
      }"
      @click="$emit('click', $event)">
      <q-item-section side>
        <q-icon
          name="mdi-drag"
          class="drag-handle"
          draggable="true"
          :color="active ? 'indigo-10' : undefined" />
      </q-item-section>

      <q-item-section side>
        <q-icon name="mdi-monitor" :color="active ? 'indigo-10' : undefined" />
      </q-item-section>

      <q-item-section>
        {{ title }}
      </q-item-section>

      <q-item-section v-if="hovered" side>
        <slot name="actions" />
      </q-item-section>
    </q-item>
  </MHoverable>
</template>

<script lang="ts" setup>
import MHoverable from 'src/core/MHoverable.vue';

defineProps<{
  title: string;
  active?: boolean;
}>();
</script>
