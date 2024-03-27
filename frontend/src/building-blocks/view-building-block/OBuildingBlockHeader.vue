<template>
  <div class="col">
    <div class="row">
      <MHoverable
        v-ripple
        class="relative-position"
        @click="$emit('edit-name')">
        <div class="row text-h6 items-center" style="gap: 0.5rem">
          <span>
            {{ name ?? 'Neuer Baustein' }}
          </span>
          <q-icon name="mdi-pencil-outline" />
        </div>
      </MHoverable>
    </div>

    <q-tabs v-model="tab" no-caps dense align="left" class="q-mt-xs">
      <q-tab name="overview"> Übersicht </q-tab>
      <q-tab name="issues"> Issues </q-tab>
      <q-tab name="requirements"> Anforderungen </q-tab>
      <q-tab name="mockups"> Mockups </q-tab>
      <q-tab name="diagrams"> Diagramme </q-tab>
      <q-tab name="discussion"> Diskussionen </q-tab>
    </q-tabs>

    <q-separator />
  </div>
</template>

<script lang="ts" setup>
import MHoverable from 'src/core/MHoverable.vue';

export type BuildingBlockHeaderTab =
  | 'overview'
  | 'issues'
  | 'requirements'
  | 'mockups'
  | 'diagrams'
  | 'discussion';

defineProps<{
  name: string;
}>();

defineEmits(['edit-name']);

const tab = defineModel<BuildingBlockHeaderTab>({ required: true });
</script>
