<template>
  <q-list dense>
    <q-item>
      <q-item-section>
        <q-item-label class="text-weight-medium"> Lernziele </q-item-label>
      </q-item-section>
    </q-item>

    <q-list dense>
      <q-item v-for="goal in learningGoals" :key="goal">
        <q-item-section>
          <q-item-label>
            {{ goal }}
          </q-item-label>
        </q-item-section>
      </q-item>

      <q-item-label
        v-if="learningGoals.length === 0"
        header
        class="text-grey-7">
        Keine Angabe
      </q-item-label>
    </q-list>
  </q-list>
</template>

<script lang="ts" setup>
defineProps<{
  learningGoals: string[];
}>();
</script>
