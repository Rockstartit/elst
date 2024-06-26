<template>
  <q-list>
    <p class="text-body1 text-weight-medium q-mb-sm"> E-Learning Kurse </p>

    <div v-if="loading" class="column" style="gap: 0.75rem">
      <q-skeleton v-for="i in 3" :key="'course-skeleton-' + i" type="rect" />
    </div>

    <div v-if="initialized && !loading">
      <q-item-label class="text-body2 text-grey-7 q-mb-md">
        Der Unterricht wurde
        <span v-if="courses.length === 0" class="text-weight-medium">
          noch nicht
        </span>
        <span v-if="courses.length > 0" class="text-weight-medium">
          {{ courses.length }} Mal
        </span>
        als E-Learning Kurs umgesetzt.
      </q-item-label>

      <div class="column" style="gap: 0.75rem">
        <q-item
          v-for="course in courses"
          :key="course.id"
          clickable
          dense
          class="elst__rounded bg-grey-3"
          @click="viewCourse(course.lessonId, course.id)">
          <q-item-section>
            <q-item-label>
              {{ course.technologyWish }}
            </q-item-label>
          </q-item-section>
        </q-item>

        <q-item
          clickable
          dense
          class="text-primary elst__rounded"
          @click="openCreateCourseDialog">
          <q-item-section side>
            <q-icon name="mdi-plus" color="primary" />
          </q-item-section>
          <q-item-section> Neuen Kurs planen </q-item-section>
        </q-item>
      </div>
    </div>
  </q-list>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { CourseOverview } from 'src/services/generated/openapi';
import { withLoading } from 'src/core/useWithLoading';
import { courseApi } from 'src/services/course_conceptualization';
import { useQuasar } from 'quasar';
import { useNotifications } from 'src/core/useNotifications';
import { isRequired, stringPromptDialog } from 'src/core/useBaseDialog';
import { useAppRouter } from 'src/router/useAppRouter';

const quasar = useQuasar();
const { viewCourse } = useAppRouter();
const notifications = useNotifications();

const props = defineProps<{
  lessonId: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const performingCreateCourse = ref(false);

const courses = ref<CourseOverview[]>([]);

onMounted(() => {
  withLoading(
    courseApi
      .getAllCourses(props.lessonId)
      .then((response) => {
        courses.value = response.data;
      })
      .finally(() => {
        initialized.value = true;
      }),
    loading
  );
});

function openCreateCourseDialog() {
  quasar
    .dialog(
      stringPromptDialog('Neuer E-Learning Kurs', 'Technologiewunsch', {
        ok: {
          label: 'Erstellen',
        },
        isValid: isRequired,
        message:
          'In welcher Technologie soll der Kurs umgesetzt werden?\nZ.B. Wordpress, "Unklar"',
      })
    )
    .onOk((technologyWish) => {
      withLoading(
        courseApi
          .createCourse(props.lessonId, {
            technologyWish,
          })
          .then((response) => {
            notifications.created();

            viewCourse(props.lessonId, response.data);
          }),
        performingCreateCourse
      );
    });
}
</script>
