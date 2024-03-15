<template>
  <PBase content-width="1200px">
    <div v-if="initialized && courseUnit">
      <OCourseUnitHeader :name="courseUnit.description" />

      <div class="row q-mt-lg">
        <div class="col">
          <OPageList :course-unit-id="courseUnitId" />

          <div class="row justify-center q-mt-md">
            <PrimaryButton
              label="Seite hinzufügen"
              :loading="performingAddPage" />
          </div>
        </div>

        <div class="column col-auto" style="gap: 1rem">
          <OTopics :topics="topics" />

          <q-separator />

          <OLearningGoals :learning-goals="courseUnit.learningGoals" />

          <q-separator />

          <OStudyMaterials :study-materials="courseUnit.studyMaterials" />
        </div>
      </div>
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { onMounted, ref } from 'vue';
import {
  CourseTopic,
  CourseUnit,
} from 'src/services/generated/openapi/courses';
import { withLoading } from 'src/core/useWithLoading';
import { courseUnitApi } from 'src/services';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import OCourseUnitHeader from 'src/courses/view-course-unit/OCourseUnitHeader.vue';
import OPageList from 'src/courses/view-course-unit/OPageList.vue';
import OTopics from 'src/courses/view-course-unit/OTopics.vue';
import OLearningGoals from 'src/courses/view-course-unit/OLearningGoals.vue';
import OStudyMaterials from 'src/courses/view-course-unit/OStudyMaterials.vue';

const props = defineProps<{
  courseId: string;
  version: number;
  courseUnitId: string;
}>();

const initialized = ref(false);
const loading = ref(false);
const courseUnit = ref<CourseUnit>();
const topics = ref<CourseTopic[]>([]);

const performingAddPage = ref(false);

onMounted(() => {
  const fetchCourseUnitPromise = courseUnitApi
    .getCourseUnit(props.courseUnitId)
    .then((response) => {
      courseUnit.value = response.data;
    });

  const fetchTopicsPromise = courseUnitApi
    .getTopics(props.courseUnitId)
    .then((response) => {
      topics.value = response.data;
    });

  withLoading(
    Promise.allSettled([fetchTopicsPromise, fetchCourseUnitPromise]).finally(
      () => {
        initialized.value = true;
      }
    ),
    loading
  );
});
</script>
