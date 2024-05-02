<template>
  <PBase content-width="1200px">
    <div
      v-if="initialized && lesson && course"
      class="column"
      style="gap: 1.5rem">
      <OCourseHeader
        :lesson-id="lessonId"
        :topic="lesson.topic"
        :technology-wish="course.technologyWish"
        @edit-technology-wish="openEditTechnologyWishDialog" />

      <div>
        <BaseInput
          v-model="course.notes"
          type="textarea"
          autogrow
          placeholder="Hier können Hinweise zur Umsetzung des E-Learning Kurses dokumentiert werden."
          hint="Umsetzungshinweise für Entwickler"
          input-style="min-height: 300px" />

        <PrimaryButton
          v-if="canSaveNotes"
          label="Speichern"
          :loading="performingSaveNotes"
          class="q-mt-md elst__base-button-width"
          @click="saveNotes" />
      </div>

      <div>
        <p class="text-h6 text-weight-medium q-mb-sm"> Kursstruktur </p>
        <p class="text-body2 text-grey-7">
          Die Kursstruktur orientiert sich an den Unterrichtseinheiten aus der
          Unterrichtsplanung.
        </p>
        <div class="column" style="gap: 1.5rem">
          <OCourseTeachingUnit
            v-for="(teachingUnit, index) in courseTeachingUnits"
            :key="teachingUnit.id"
            v-model="courseTeachingUnits[index]"
            :course-id="courseId" />
        </div>
      </div>
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { computed, onMounted, ref } from 'vue';
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
import BaseInput from 'src/core/BaseInput.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import OCourseTeachingUnit from 'src/courses/view-course/OCourseTeachingUnit.vue';

const quasar = useQuasar();
const notifications = useNotifications();

const props = defineProps<{
  lessonId: string;
  courseId: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const course = ref<Course>();
const lesson = ref<Lesson>();
const courseTeachingUnits = ref<CourseTeachingUnit[]>([]);

const lastSavedNotes = ref<string>();
const performingSaveNotes = ref(false);
const canSaveNotes = computed(
  () => course.value?.notes !== lastSavedNotes.value
);

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
      lastSavedNotes.value = response.data.notes;
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

function saveNotes() {
  return withLoading(
    courseApi
      .editCourse(props.courseId, {
        notes: course.value?.notes,
      })
      .then(() => {
        lastSavedNotes.value = course.value?.notes;
        notifications.saved();
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingSaveNotes
  );
}
</script>
