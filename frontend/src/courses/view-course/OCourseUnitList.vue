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
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import { CourseUnit } from 'src/services/generated/openapi/courses';

defineProps<{
  courseUnits: CourseUnit[];
  initialized?: boolean;
  fetching?: boolean;
}>();

function keyFn(courseUnit: CourseUnit) {
  return courseUnit.id;
}
</script>
