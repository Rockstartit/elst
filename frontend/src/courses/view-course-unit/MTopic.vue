<template>
  <div>
    <q-item dense>
      <q-item-section>
        <q-item-label>
          {{ topic.title }}
        </q-item-label>
      </q-item-section>
    </q-item>
    <q-list dense>
      <q-item
        v-for="subtopic in topic.subtopics"
        :key="subtopic.id"
        :inset-level="0.3">
        <q-item-section>
          <q-item-label class="text-grey-8">
            {{ subtopic.title }}
          </q-item-label>
        </q-item-section>
      </q-item>
    </q-list>
  </div>
</template>

<script lang="ts" setup>
import { CourseTopic } from 'src/services/generated/openapi/courses';

defineProps<{
  topic: CourseTopic;
}>();
</script>
