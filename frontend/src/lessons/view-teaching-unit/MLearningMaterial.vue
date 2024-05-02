<template>
  <q-item v-bind="$props" class="bg-grey-3 elst__rounded elst__bordered">
    <q-item-section side>
      <q-icon name="mdi-file-outline" />
    </q-item-section>
    <q-item-section>
      <q-item-label class="text-weight-medium">
        {{ learningMaterial.name }}
      </q-item-label>
    </q-item-section>

    <slot name="after" />
  </q-item>
</template>

<script setup lang="ts">
import { LearningMaterial } from 'src/services/generated/openapi';
import { QItemProps } from 'quasar';

withDefaults(
  defineProps<
    {
      learningMaterial: LearningMaterial;
    } & QItemProps
  >(),
  {}
);
</script>
