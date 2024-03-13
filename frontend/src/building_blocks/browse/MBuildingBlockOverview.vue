<template>
  <q-item v-bind="$props">
    <q-item-section>
      <q-item-label>
        {{ buildingBlock.name }}
      </q-item-label>
      <q-item-label v-if="buildingBlock.description" caption>
        {{ buildingBlock.description }}
      </q-item-label>
    </q-item-section>
  </q-item>
</template>

<script lang="ts" setup>
import { ReleasedBuildingBlock } from 'src/services/generated/openapi/building_blocks';
import { QItemProps } from 'quasar';

defineProps<
  {
    buildingBlock: ReleasedBuildingBlock;
  } & QItemProps
>();
</script>
