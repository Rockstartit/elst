<template>
  <OBaseAnimatedList
    :key-fn="keyFn"
    :items="lessons"
    :initialized="initialized"
    :fetching="fetching"
    empty-message="Es wurde noch kein Unterricht geplant.">
    <template #item="{ item }">
      <slot name="item" :lesson="item" />
    </template>
  </OBaseAnimatedList>
</template>

<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import {Lesson} from "src/services/generated/openapi";
import {withLoading} from "src/core/useWithLoading";
import {lessonApi} from "src/services/lesson_planning";

const initialized = ref(false);
const fetching = ref(false);

const lessons = ref<Lesson[]>([])

onMounted(() => {
  withLoading(lessonApi.getAllLessons().then(response => {
    lessons.value = response.data;
  }).finally(() => {
    initialized.value = true;
  }), fetching)
});

function keyFn(lesson: Lesson) {
  return lesson.id;
}
</script>
