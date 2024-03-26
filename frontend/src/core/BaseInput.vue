<template>
  <q-input v-bind="$props">
    <slot />
  </q-input>
</template>

<script lang="ts" setup>
import { QInputProps } from 'quasar';

withDefaults(defineProps<QInputProps>(), {
  outlined: true,
  hideBottomSpace: true,
  borderless: true,
  stackLabel: true,
  dense: true,
  lazyRules: true,
  bgColor: 'grey-3',
  labelColor: 'grey-10',
});
</script>
