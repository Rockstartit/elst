<template>
  <div class="overflow-hidden">
    <q-item class="bg-indigo-1 text-indigo-10 elst__rounded">
      <q-item-section side>
        <q-icon name="mdi-table-of-contents" color="indigo-10" />
      </q-item-section>

      <q-item-section>
        <q-item-label class="text-body1 text-weight-medium">
          {{ teachingUnit.topic }}
        </q-item-label>
      </q-item-section>
    </q-item>

    <q-list class="column q-mt-md q-px-md" style="gap: 2rem">
      <div
        v-for="(teachingPhase, index) in teachingUnit.teachingPhases"
        :key="teachingPhase.id">
        <OCourseTeachingPhase
          v-model="teachingUnit.teachingPhases[index]"
          :course-id="courseId" />
      </div>
    </q-list>
  </div>
</template>

<script setup lang="ts">
import { CourseTeachingUnit } from 'src/services/generated/openapi';
import OCourseTeachingPhase from 'src/courses/view-course/OCourseTeachingPhase.vue';

defineProps<{
  courseId: string;
}>();

const teachingUnit = defineModel<CourseTeachingUnit>({ required: true });
</script>
