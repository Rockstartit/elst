<template>
  <q-list dense>
    <q-item>
      <q-item-section>
        <q-item-label class="text-weight-medium"> Themen </q-item-label>
      </q-item-section>
    </q-item>

    <MTopic v-for="topic in topics" :key="topic.id" :topic="topic" />
  </q-list>
</template>

<script lang="ts" setup>
import { CourseTopic } from 'src/services/generated/openapi/courses';
import MTopic from 'src/courses/view-course-unit/MTopic.vue';

defineProps<{
  topics: CourseTopic[];
}>();
</script>
