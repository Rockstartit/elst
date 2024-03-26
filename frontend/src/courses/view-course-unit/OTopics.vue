<template>
  <q-list dense>
    <q-item>
      <q-item-section>
        <q-item-label class="text-weight-medium"> Themen </q-item-label>
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

    <MTopic v-for="topic in topics" :key="topic.id" :topic="topic" />
  </q-list>
</template>

<script lang="ts" setup>
import { CourseTopic } from 'src/services/generated/openapi/courses';
import MTopic from 'src/courses/view-course-unit/MTopic.vue';
import SecondaryButton from 'src/core/SecondaryButton.vue';

defineProps<{
  topics: CourseTopic[];
}>();

defineEmits(['edit']);
</script>
