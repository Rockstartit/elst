<template>
  <q-page class="row justify-center text-grey-10 q-px-md">
    <teleport v-if="isMounted" to="#breadcrumbs">
      <slot name="breadcrumbs" />
    </teleport>

    <div class="q-py-md" :style="{ width: contentWidth }">
      <slot />
    </div>
  </q-page>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';

withDefaults(
  defineProps<{
    contentWidth?: string;
  }>(),
  {
    contentWidth: '100%',
  }
);

const isMounted = ref(false);

onMounted(() => {
  isMounted.value = true;
});
</script>
