<template>
  <PBase content-width="1200px">
    <div v-if="initialized && courseUnit">
      <OCourseUnitHeader
        :name="courseUnit.description"
        @edit-name="openEditDescriptionDialog" />

      <div class="overflow-hidden q-mt-lg">
        <div class="row q-col-gutter-lg">
          <div class="col">
            <OPageList :course-unit-id="courseUnitId">
              <template #item="{ page }">
                <MPageOverview
                  :page="page"
                  clickable
                  @click="viewPage(courseVersion, courseUnitId, page.id)" />
              </template>
            </OPageList>

            <div class="row justify-center q-mt-md">
              <PrimaryButton
                label="Seite hinzufügen"
                :loading="performingAddPage"
                @click="createPage" />
            </div>
          </div>

          <div class="column col-auto">
            <div class="column elst__detail-sidebar" style="gap: 1rem">
              <OTopics :topics="topics" @edit="openEditTopicsDialog" />

              <q-separator />

              <OLearningGoals
                :learning-goals="courseUnit.learningGoals"
                @edit="openEditLearningGoalsDialog" />

              <q-separator />

              <OStudyMaterials
                :study-materials="courseUnit.studyMaterials"
                @edit="openEditStudyMaterialsDialog" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { computed, onMounted, ref } from 'vue';
import {
  CourseTopic,
  CourseUnit,
  CourseVersion,
} from 'src/services/generated/openapi/courses';
import { withLoading } from 'src/core/useWithLoading';
import { courseUnitApi, pageApi } from 'src/services';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import OCourseUnitHeader from 'src/courses/view-course-unit/OCourseUnitHeader.vue';
import OPageList from 'src/courses/view-course-unit/OPageList.vue';
import OTopics from 'src/courses/view-course-unit/OTopics.vue';
import OLearningGoals from 'src/courses/view-course-unit/OLearningGoals.vue';
import OStudyMaterials from 'src/courses/view-course-unit/OStudyMaterials.vue';
import MPageOverview from 'src/courses/view-course-unit/MPageOverview.vue';
import { useAppRouter } from 'src/router/useAppRouter';
import { useQuasar } from 'quasar';
import EditStudyMaterialsDialog, {
  EditStudyMaterialsDialogProps,
} from 'src/courses/view-course-unit/EditStudyMaterialsDialog.vue';
import EditLearningGoalsDialog, {
  EditLearningGoalsDialogProps,
} from 'src/courses/view-course-unit/EditLearningGoalsDialog.vue';

const quasar = useQuasar();
const { viewPage } = useAppRouter();

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

const courseVersion = computed<CourseVersion>(() => {
  return {
    courseId: props.courseId,
    version: props.version,
  };
});

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

function createPage() {
  return withLoading(
    pageApi
      .createPage(props.courseUnitId, {
        title: 'Neue Seite',
      })
      .then((response) => {
        viewPage(courseVersion.value, props.courseUnitId, response.data);
      }),
    performingAddPage
  );
}

function openEditDescriptionDialog() {
  quasar
    .dialog({
      title: 'Titel bearbeiten',
      prompt: {
        model: courseUnit.value?.description ?? '',
      },
      cancel: true,
    })
    .onOk((payload) => {
      courseUnitApi
        .editCourseUnit(props.courseUnitId, {
          description: payload,
        })
        .then(() => {
          if (courseUnit.value) {
            courseUnit.value.description = payload;
          }
        });
    });
}

function openEditTopicsDialog() {
  //
}

function openEditLearningGoalsDialog() {
  const dialogProps: EditLearningGoalsDialogProps = {
    learningGoals: courseUnit.value?.learningGoals ?? [],
  };

  quasar
    .dialog({
      component: EditLearningGoalsDialog,
      componentProps: dialogProps,
    })
    .onOk((payload: EditLearningGoalsDialogProps) => {
      courseUnitApi
        .editCourseUnit(props.courseUnitId, {
          learningGoals: payload.learningGoals,
        })
        .then(() => {
          if (courseUnit.value) {
            courseUnit.value.learningGoals = payload.learningGoals;
          }
        });
    });
}

function openEditStudyMaterialsDialog() {
  const dialogProps: EditStudyMaterialsDialogProps = {
    eReading: courseUnit.value?.studyMaterials?.eReading,
    eBook: courseUnit.value?.studyMaterials?.eBook,
    bibliography: courseUnit.value?.studyMaterials?.bibliography,
    relatedLinks: courseUnit.value?.studyMaterials?.relatedLinks,
  };

  quasar
    .dialog({
      component: EditStudyMaterialsDialog,
      componentProps: dialogProps,
    })
    .onOk((payload: EditStudyMaterialsDialogProps) => {
      courseUnitApi
        .editCourseUnit(props.courseUnitId, {
          studyMaterials: {
            eReading: payload.eReading,
            eBook: payload.eBook,
            bibliography: payload.bibliography,
            relatedLinks: payload.relatedLinks,
          },
        })
        .then(() => {
          if (courseUnit.value?.studyMaterials) {
            courseUnit.value.studyMaterials.eReading = payload.eReading;
            courseUnit.value.studyMaterials.eBook = payload.eBook;
            courseUnit.value.studyMaterials.bibliography = payload.bibliography;
            courseUnit.value.studyMaterials.relatedLinks = payload.relatedLinks;
          }
        });
    });
}
</script>
