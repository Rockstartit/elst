<template>
  <q-list dense>
    <q-item>
      <q-item-section>
        <q-item-label class="text-weight-medium"> Allgemein </q-item-label>
      </q-item-section>
      <q-item-section side>
        <SecondaryButton
          icon="mdi-cog-outline"
          dense
          flat
          size="sm"
          @click="$emit('edit')" />
      </q-item-section>
    </q-item>

    <div class="text-body2 q-px-md q-mt-sm">
      <p v-if="buildingBlock.description" class="q-mb-none">
        {{ buildingBlock.description }}
      </p>

      <p v-else class="text-grey-7 q-mb-none"> Keine Beschreibung </p>
    </div>

    <q-separator class="q-my-md" />

    <MContributors label="Lehrer" />

    <MContributors label="Entwickler" />
  </q-list>
</template>

<script lang="ts" setup>
import SecondaryButton from 'src/core/SecondaryButton.vue';
import MContributors from 'src/building-blocks/view-building-block/overview/MContributors.vue';
import { BuildingBlock } from 'src/services/generated/openapi';

defineProps<{
  buildingBlock: BuildingBlock;
}>();

defineEmits(['edit']);
</script>
