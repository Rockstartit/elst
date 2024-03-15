<template>
  <PBase content-width="1200px">
    <div v-if="initialized && course">
      <OCourseHeader :name="course.name" />

      <div class="overflow-hidden q-mt-lg">
        <div class="row q-col-gutter-lg">
          <div class="col">
            <OCourseUnitList :course-version="courseVersion">
              <template #item="{ courseUnit }">
                <MCourseUnitOverview
                  :course-unit="courseUnit"
                  clickable
                  @click="viewCourseUnit(courseVersion, courseUnit.id)" />
              </template>
            </OCourseUnitList>

            <div class="row justify-center q-mt-md">
              <PrimaryButton
                label="Neue Kurseinheit"
                :loading="performingCreateCourseUnit"
                @click="createCourseUnit" />
            </div>
          </div>

          <div class="col-auto">
            <OCourseDetails :course="course" />
          </div>
        </div>
      </div>
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import OCourseHeader from 'src/courses/view-course/OCourseHeader.vue';
import OCourseUnitList from 'src/courses/view-course/OCourseUnitList.vue';
import { computed, onMounted, ref } from 'vue';
import { Course, CourseVersion } from 'src/services/generated/openapi/courses';
import { withLoading } from 'src/core/useWithLoading';
import { courseApi, courseUnitApi } from 'src/services';
import OCourseDetails from 'src/courses/view-course/OCourseDetails.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useAppRouter } from 'src/router/useAppRouter';
import MCourseUnitOverview from 'src/courses/view-course/MCourseUnitOverview.vue';

const { viewCourseUnit } = useAppRouter();

const props = defineProps<{
  courseId: string;
  version: number;
}>();

const initialized = ref(false);
const loading = ref(false);
const course = ref<Course>();

const performingCreateCourseUnit = ref(false);

const courseVersion = computed<CourseVersion>(() => {
  return {
    courseId: props.courseId,
    version: props.version,
  };
});

onMounted(() => {
  withLoading(
    courseApi
      .getCourse(props.courseId, props.version)
      .then((response) => {
        course.value = response.data;
      })
      .finally(() => {
        initialized.value = true;
      }),
    loading
  );
});

function createCourseUnit() {
  return withLoading(
    courseUnitApi
      .createCourseUnit(props.courseId, props.version)
      .then((response) => {
        viewCourseUnit(courseVersion.value, response.data);
      }),
    performingCreateCourseUnit
  );
}
</script>
