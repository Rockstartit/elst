<template>
  <q-uploader v-bind="$props"> </q-uploader>
</template>

<script setup lang="ts">
import { QUploaderProps } from 'quasar';

withDefaults(defineProps<QUploaderProps>(), {
  flat: true,
  bordered: true,
  noThumbnails: true,
  fieldName: 'fileName',
});
</script>
