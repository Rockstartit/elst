<template>
  <div class="full-width">
    <q-uploader v-if="showUpload" v-bind="$props" class="bg-grey-2 full-width">
    </q-uploader>

    <PrimaryButton
      v-else
      flat
      :label="label ?? 'Hochladen'"
      text-color="primary"
      icon="mdi-upload-outline"
      @click="showUpload = true" />
  </div>
</template>

<script setup lang="ts">
import { QUploaderProps } from 'quasar';
import { ref } from 'vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';

withDefaults(defineProps<{ label?: string } & QUploaderProps>(), {
  flat: true,
  bordered: true,
  noThumbnails: true,
  fieldName: 'fileName',
});

const showUpload = ref(false);
</script>
