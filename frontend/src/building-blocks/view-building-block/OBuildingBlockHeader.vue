<template>
  <div class="col">
    <div class="row">
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
      </div>

      <div class="col-auto">
        <div class="row">
          <q-chip
            color="indigo-4"
            text-color="white"
            rounded
            outline
            clickable
            class="shadow-0">
            <div
              class="row text-body2 items-center q-px-lg"
              style="gap: 0.5rem">
              <q-icon name="mdi-star-outline" size="20px" />

              <span> Interessiert </span>
            </div>
          </q-chip>

          <q-chip
            color="primary"
            text-color="white"
            rounded
            clickable
            class="shadow-0"
            @click="$emit('edit-technology')">
            <div
              class="row text-body2 items-center q-px-lg"
              style="gap: 0.5rem">
              <q-icon name="mdi-code-tags" size="20px" />

              <span>
                {{ technology }}
              </span>
            </div>
          </q-chip>
        </div>
      </div>
    </div>

    <q-tabs v-model="tab" no-caps dense align="left" class="q-mt-xs">
      <q-tab name="overview"> Übersicht </q-tab>
      <q-tab name="properties"> Konfigurationsmöglichkeiten </q-tab>
      <q-tab name="issues"> Issues </q-tab>
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
  technology: string;
}>();

defineEmits(['edit-name', 'edit-technology']);

const tab = defineModel<BuildingBlockHeaderTab>({ required: true });
</script>
