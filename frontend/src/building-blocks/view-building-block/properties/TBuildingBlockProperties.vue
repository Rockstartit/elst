<template>
  <div>
    <q-list>
      <MBuildingBlockProperty
        v-for="property in sortedProperties"
        :key="property.key"
        :property="property" />

      <p v-if="buildingBlock.properties.length === 0">
        Keine Konfigurationsmöglichkeiten
      </p>
    </q-list>

    <PrimaryButton label="Neue Konfigurationsmöglichkeit" />
  </div>
</template>

<script lang="ts" setup>
import { BuildingBlock } from 'src/services/generated/openapi';
import MBuildingBlockProperty from 'src/building-blocks/view-building-block/properties/MBuildingBlockProperty.vue';
import { computed } from 'vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';

const buildingBlock = defineModel<BuildingBlock>({ required: true });

const sortedProperties = computed(() =>
  [...buildingBlock.value.properties].sort((a, b) => a.order - b.order)
);
</script>
