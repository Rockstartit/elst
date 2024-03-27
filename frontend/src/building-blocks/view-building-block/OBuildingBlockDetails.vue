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

      <p class="text-grey-7 q-mb-none"> Keine Beschreibung </p>
    </div>

    <q-separator class="q-my-md" />

    <MCourseDetail
      icon="mdi-source-branch"
      label="Version"
      :value="'Version ' + buildingBlock.version.version" />
    <MCourseDetail
      icon="mdi-rocket-launch-outline"
      label="Veröffentlichung"
      :value="inDevelopment ? 'Konzeption' : 'Veröffentlicht'" />
  </q-list>
</template>

<script lang="ts" setup>
import MCourseDetail from 'src/courses/view-course/MCourseDetail.vue';
import SecondaryButton from 'src/core/SecondaryButton.vue';
import { BuildingBlock } from 'src/services/generated/openapi/building_blocks';
import { computed } from 'vue';

const props = defineProps<{
  buildingBlock: BuildingBlock;
}>();

defineEmits(['edit']);

const inDevelopment = computed(
  () => props.buildingBlock.releaseStatus === 'IN_DEVELOPMENT'
);
</script>
