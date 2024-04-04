<template>
  <q-select v-bind="$props">
    <slot />
  </q-select>
</template>

<script lang="ts" setup>
import { QSelectProps } from 'quasar';

withDefaults(defineProps<QSelectProps>(), {
  outlined: true,
  hideBottomSpace: true,
  borderless: true,
  stackLabel: true,
  dense: true,
  lazyRules: true,
  bgColor: 'grey-3',
  labelColor: 'grey-10',
  mapOptions: true,
  emitValue: true,
});
</script>
