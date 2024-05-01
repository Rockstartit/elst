<template>
  <q-card v-bind="$props" class="elst__rounded overflow-hidden">
    <q-card-section
      class="q-py-sm q-px-md"
      :class="['bg-' + headerColor, 'text-' + headerTextColor]">
      <span class="text-body1 text-weight-medium">
        {{ title }}
      </span>
    </q-card-section>
    <slot />
  </q-card>
</template>

<script setup lang="ts">
import { QCardProps } from 'quasar';

withDefaults(
  defineProps<
    {
      title: string;
      headerColor?: string;
      headerTextColor?: string;
    } & QCardProps
  >(),
  {
    flat: true,
    bordered: true,
    headerColor: 'indigo-1',
    headerTextColor: 'indigo-8',
  }
);
</script>
