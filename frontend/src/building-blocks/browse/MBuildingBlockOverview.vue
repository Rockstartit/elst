<template>
  <q-item
    v-bind="$props"
    class="bg-grey-1 elst__rounded elst__bordered q-pa-md">
    <q-item-section avatar>
      <q-icon name="mdi-open-in-new" size="1.5rem" />
    </q-item-section>
    <q-item-section>
      <q-item-label>
        {{ buildingBlock.name }}
      </q-item-label>
      <q-item-label v-if="buildingBlock.description" caption>
        {{ buildingBlock.description }}
      </q-item-label>
    </q-item-section>
    <q-item-section side>
      <q-badge>{{ 'Version ' + buildingBlock.version.version }}</q-badge>
    </q-item-section>
  </q-item>
</template>

<script lang="ts" setup>
import { BuildingBlock } from 'src/services/generated/openapi/building_blocks';
import { QItemProps } from 'quasar';

defineProps<
  {
    buildingBlock: BuildingBlock;
  } & QItemProps
>();
</script>
