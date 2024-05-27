<template>
  <q-item v-bind="$props">
    <q-item-section>
      <q-item-label>
        {{ property.displayName }}
      </q-item-label>
      <q-item-label caption>
        {{ property.description }}
      </q-item-label>
    </q-item-section>
  </q-item>
</template>

<script lang="ts" setup>
import { QItemProps } from 'quasar';
import { BuildingBlockProperty } from 'src/services/generated/openapi';

withDefaults(
  defineProps<
    {
      property: BuildingBlockProperty;
    } & QItemProps
  >(),
  {}
);
</script>
