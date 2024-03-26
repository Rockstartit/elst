<template>
  <PBase content-width="1200px">
    <div v-if="initialized && course">
      <OCourseHeader :name="course.name" />

      <div class="overflow-hidden q-mt-lg">
        <div class="row q-col-gutter-lg">
          <div class="col">
            <OCourseUnitList
              :course-units="courseUnits"
              :initialized="courseUnitsInitialized"
              :fetching="fetching">
              <template #item="{ courseUnit }">
                <MCourseUnitOverview
                  :course-unit="courseUnit"
                  clickable
                  @click="viewCourseUnit(courseVersion, courseUnit.id)">
                  <template #after="{ hover }">
                    <q-item-section side>
                      <PrimaryButton
                        :style="hover ? undefined : 'visibility: hidden'"
                        icon="mdi-delete"
                        dense
                        flat
                        text-color="grey-6"
                        hover-text-color="red-10"
                        hover-color="red-1"
                        :loading="
                          performingDeleteCourseUnit.includes(courseUnit.id)
                        "
                        @click.stop="deleteCourseUnit(courseUnit)" />
                    </q-item-section>
                  </template>
                </MCourseUnitOverview>
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
            <OCourseDetails :course="course" class="elst__detail-sidebar" />
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
import {
  Course,
  CourseUnit,
  CourseVersion,
} from 'src/services/generated/openapi/courses';
import { withLoading, withLoadingArray } from 'src/core/useWithLoading';
import { courseApi, courseUnitApi } from 'src/services';
import OCourseDetails from 'src/courses/view-course/OCourseDetails.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useAppRouter } from 'src/router/useAppRouter';
import MCourseUnitOverview from 'src/courses/view-course/MCourseUnitOverview.vue';
import { useCourseUnits } from 'src/courses/view-course/useCourseUnits';

const { viewCourseUnit } = useAppRouter();
const { fetching, courseUnits, fetchCourseUnits } = useCourseUnits();

const props = defineProps<{
  courseId: string;
  version: number;
}>();

const initialized = ref(false);
const loading = ref(false);
const course = ref<Course>();

const performingCreateCourseUnit = ref(false);
const performingDeleteCourseUnit = ref<string[]>([]);

const courseVersion = computed<CourseVersion>(() => {
  return {
    courseId: props.courseId,
    version: props.version,
  };
});

const courseUnitsInitialized = ref(false);

onMounted(() => {});

onMounted(() => {
  const fetchCourseUnitsPromise = fetchCourseUnits(courseVersion.value).finally(
    () => {
      courseUnitsInitialized.value = true;
    }
  );

  const fetchCoursePromise = courseApi
    .getCourse(props.courseId, props.version)
    .then((response) => {
      course.value = response.data;
    });

  withLoading(
    Promise.allSettled([fetchCoursePromise, fetchCourseUnitsPromise]).finally(
      () => {
        initialized.value = true;
      }
    ),
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

function deleteCourseUnit(courseUnit: CourseUnit) {
  return withLoadingArray(
    courseUnitApi.deleteCourseUnit(courseUnit.id).then(() => {
      const index = courseUnits.value.indexOf(courseUnit);

      if (index >= 0) {
        courseUnits.value.splice(index, 1);
      }
    }),
    performingDeleteCourseUnit,
    courseUnit.id
  );
}
</script>
