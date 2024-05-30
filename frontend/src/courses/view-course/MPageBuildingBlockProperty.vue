<template>
  <q-item v-bind="$props">
    <q-item-section>
      <q-item-label>
        {{ property.displayName }}
      </q-item-label>
      <q-item-label v-if="property.description" caption>
        {{ property.description }}
      </q-item-label>
    </q-item-section>
    <q-item-label>
      {{ property.value.length > 0 ? property.value : 'Kein Wert' }}
    </q-item-label>
  </q-item>
</template>

<script lang="ts" setup>
import { PageBuildingBlockProperty } from 'src/services/generated/openapi';
import { QItemProps } from 'quasar';

defineProps<
  {
    property: PageBuildingBlockProperty;
  } & QItemProps
>();
</script>
