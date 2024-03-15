<template>
  <OBaseAnimatedList
    :key-fn="keyFn"
    :items="courseUnits"
    :initialized="initialized"
    :fetching="fetching"
    empty-message="Keine Kurseinheiten hinzugefügt">
    <template #item="{ item }">
      <slot name="item" :courseUnit="item" />
    </template>
  </OBaseAnimatedList>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import {
  CourseUnit,
  CourseVersion,
} from 'src/services/generated/openapi/courses';
import { useCourseUnits } from 'src/courses/view-course/useCourseUnits';

const { fetching, courseUnits, fetchCourseUnits } = useCourseUnits();

const props = defineProps<{
  courseVersion: CourseVersion;
}>();

const initialized = ref(false);

onMounted(() => {
  fetchCourseUnits(props.courseVersion).finally(() => {
    initialized.value = true;
  });
});

function keyFn(courseUnit: CourseUnit) {
  return courseUnit.id;
}
</script>
