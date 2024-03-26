<template>
  <q-checkbox v-bind="$props">
    <slot />
  </q-checkbox>
</template>

<script lang="ts" setup>
import { QCheckboxProps } from 'quasar';

withDefaults(defineProps<QCheckboxProps>(), {
  size: 'sm',
});
</script>
