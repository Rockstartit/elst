<template>
  <q-list dense>
    <q-item>
      <q-item-section>
        <q-item-label class="text-weight-medium"> Allgemein </q-item-label>
      </q-item-section>
    </q-item>

    <q-item v-if="course.description">
      <q-item-section>
        {{ course.description }}
      </q-item-section>
    </q-item>

    <MCourseDetail
      icon="mdi-school-outline"
      label="Abschluss"
      :value="
        course.gradRequired ? 'Abschluss benötigt' : 'Ohne Abschluss möglich'
      " />
    <MCourseDetail
      v-if="course.degree"
      icon="mdi-school-outline"
      label="Abschluss"
      :value="course.degree" />
    <MCourseDetail
      v-if="course.semester"
      icon="mdi-school-outline"
      label="Semester"
      :value="course.semester" />
    <MCourseDetail
      icon="mdi-star-outline"
      label="Leistungspunkte"
      :value="course.creditPoints" />
    <MCourseDetail
      icon="mdi-lightbulb-on-outline"
      label="Vorwissen"
      :value="course.knowledge" />
    <MCourseDetail
      icon="mdi-head-cog-outline"
      label="Skills"
      :value="course.skills" />
    <MCourseDetail
      icon="mdi-calendar-outline"
      label="Zeitplan"
      :value="course.schedule" />
  </q-list>
</template>

<script lang="ts" setup>
import { Course } from 'src/services/generated/openapi/courses';
import MCourseDetail from 'src/courses/view-course/MCourseDetail.vue';

defineProps<{
  course: Course;
}>();
</script>
