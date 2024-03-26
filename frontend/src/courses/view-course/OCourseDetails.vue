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

    <MCourseDetail
      icon="mdi-school-outline"
      label="Abschluss"
      :value="degreeLabel" />
    <MCourseDetail
      icon="mdi-star-outline"
      label="Leistungspunkte"
      :value="course.creditPoints" />
    <MCourseDetail
      v-if="course.semester"
      icon="mdi-calendar-refresh-outline"
      label="Semester"
      :value="course.semester" />
    <MCourseDetail
      icon="mdi-calendar-outline"
      label="Zeitplan"
      :value="course.schedule" />
    <MCourseDetail
      icon="mdi-lightbulb-on-outline"
      label="Vorwissen"
      :value="course.knowledge" />
    <MCourseDetail
      icon="mdi-head-cog-outline"
      label="Skills"
      :value="course.skills" />
  </q-list>
</template>

<script lang="ts" setup>
import { Course } from 'src/services/generated/openapi/courses';
import MCourseDetail from 'src/courses/view-course/MCourseDetail.vue';
import SecondaryButton from 'src/core/SecondaryButton.vue';
import { computed } from 'vue';

const props = defineProps<{
  course: Course;
}>();

defineEmits(['edit']);

const degreeLabel = computed(() => {
  if (!props.course?.degree) {
    return props.course.gradRequired
      ? 'Abschluss benötigt'
      : 'Kein Abschluss benötigt';
  }

  return (
    props.course.degree +
    ' ' +
    (props.course.gradRequired ? 'benötigt' : 'empfohlen')
  );
});
</script>
