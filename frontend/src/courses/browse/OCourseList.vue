<template>
  <OBaseAnimatedList
    :key-fn="keyFn"
    :items="courses"
    :initialized="initialized"
    :fetching="fetching"
    empty-message="Keine Kurse verfügbar">
    <template #item="{ item }">
      <slot name="item" :course="item" />
    </template>
  </OBaseAnimatedList>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import { useBrowseCourses } from 'src/courses/browse/useBrowseCourses';
import { Course } from 'src/services/generated/openapi/courses';

const { fetching, courses, fetchCourses } = useBrowseCourses();

const initialized = ref(false);

onMounted(() => {
  fetchCourses().finally(() => {
    initialized.value = true;
  });
});

function keyFn(course: Course) {
  return course.code;
}
</script>
