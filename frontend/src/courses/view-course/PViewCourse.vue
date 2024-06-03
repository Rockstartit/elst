<template>
  <PBase content-width="1600px">
    <div
      v-if="initialized && lesson && course"
      class="column"
      style="gap: 1.5rem">
      <OCourseHeader
        :lesson-id="lessonId"
        :topic="lesson.topic"
        :technology-wish="course.technologyWish"
        @edit-technology-wish="openEditTechnologyWishDialog" />

      <OCourseNotes v-model="course.notes" :course-id="courseId" />

      <OCourseStructure
        v-model="courseTeachingUnits"
        :course-id="courseId"
        :lesson-id="lessonId"
        :technology-wish="course.technologyWish" />
    </div>

    <template #breadcrumbs>
      <TheBreadcrumbs>
        <q-breadcrumbs-el
          class="cursor-pointer"
          :to="viewLessonRoute(lessonId)">
          {{ lesson?.topic }}
        </q-breadcrumbs-el>
        <q-breadcrumbs-el class="text-black"> Kurse </q-breadcrumbs-el>
        <q-breadcrumbs-el>
          {{ course?.technologyWish }}
        </q-breadcrumbs-el>
      </TheBreadcrumbs>
    </template>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { onMounted, ref } from 'vue';
import { withLoading } from 'src/core/useWithLoading';
import { courseApi } from 'src/services/course_conceptualization';
import {
  Course,
  CourseTeachingUnit,
  Lesson,
} from 'src/services/generated/openapi';
import { lessonApi } from 'src/services/lesson_planning';
import OCourseHeader from 'src/courses/view-course/OCourseHeader.vue';
import { useQuasar } from 'quasar';
import { useNotifications } from 'src/core/useNotifications';
import { isRequired, stringPromptDialog } from 'src/core/useBaseDialog';
import TheBreadcrumbs from 'src/core/TheBreadcrumbs.vue';
import OCourseNotes from 'src/courses/view-course/OCourseNotes.vue';
import OCourseStructure from 'src/courses/view-course/OCourseStructure.vue';
import { useAppRouter } from 'src/router/useAppRouter';

const quasar = useQuasar();
const notifications = useNotifications();
const { viewLessonRoute } = useAppRouter();

const props = defineProps<{
  lessonId: string;
  courseId: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const course = ref<Course>();
const lesson = ref<Lesson>();
const courseTeachingUnits = ref<CourseTeachingUnit[]>([]);

onMounted(() => {
  const fetchLessonPromise = lessonApi
    .getLesson(props.lessonId)
    .then((response) => {
      lesson.value = response.data;
    });

  const fetchCoursePromise = courseApi
    .getCourse(props.courseId)
    .then((response) => {
      course.value = response.data;
    });

  const fetchCourseStructurePromise = courseApi
    .getCourseStructure(props.courseId)
    .then((response) => {
      courseTeachingUnits.value = response.data;
    });

  withLoading(
    Promise.allSettled([
      fetchLessonPromise,
      fetchCoursePromise,
      fetchCourseStructurePromise,
    ]).finally(() => {
      initialized.value = true;
    }),
    loading
  );
});

function openEditTechnologyWishDialog() {
  quasar
    .dialog(
      stringPromptDialog('Technologiewunsch bearbeiten', 'Technologiewunsch', {
        ok: {
          label: 'Speichern',
        },
        isValid: isRequired,
        model: course.value?.technologyWish,
      })
    )
    .onOk((technologyWish) => {
      courseApi
        .editCourse(props.courseId, {
          technologyWish,
        })
        .then(() => {
          notifications.saved();

          if (course.value) {
            course.value.technologyWish = technologyWish;
          }
        })
        .catch((err) => {
          notifications.apiError(err);
        });
    });
}
</script>
