<template>
  <OBaseAnimatedList
    :key-fn="keyFn"
    :items="pages"
    :initialized="initialized"
    :fetching="fetching"
    empty-message="Keine Seiten hinzugefügt">
    <template #item="{ item }">
      <slot name="item" :page="item" />
    </template>
  </OBaseAnimatedList>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import { PageOverview } from 'src/services/generated/openapi/courses';
import { useViewCourseUnit } from 'src/courses/view-course-unit/useViewCourseUnit';

const { fetching, fetchPages, pages } = useViewCourseUnit();

const props = defineProps<{
  courseUnitId: string;
}>();

const initialized = ref(false);

onMounted(() => {
  fetchPages(props.courseUnitId).finally(() => {
    initialized.value = true;
  });
});

function keyFn(page: PageOverview) {
  return page.id;
}
</script>
