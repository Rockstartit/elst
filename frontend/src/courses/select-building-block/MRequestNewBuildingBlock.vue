<template>
  <q-item
    v-bind="$props"
    class="bg-indigo-1 text-indigo-10 elst__rounded q-pa-md">
    <q-item-section avatar>
      <q-icon name="mdi-open-in-new" size="1.5rem" color="indigo-10" />
    </q-item-section>
    <q-item-section>
      <q-item-label> Neuen Baustein beantragen </q-item-label>
      <q-item-label caption class="text-indigo-7">
        Startet einen neuen Entwicklungsprozess für einen von Ihnen
        spezifizierten Baustein.
      </q-item-label>
    </q-item-section>
  </q-item>
</template>

<script lang="ts" setup>
import { QItemProps } from 'quasar';

defineProps<QItemProps>();
</script>
