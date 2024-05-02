<template>
  <PBase content-width="1200px">
    <div v-if="initialized && lesson">
      <OLessonHeader
        :topic="lesson.topic"
        class="q-mb-lg"
        @edit-topic="openEditTopicDialog" />

      <div class="row q-col-gutter-lg">
        <div class="col column" style="gap: 2rem">
          <BaseCard title="Allgemein">
            <q-card-section>
              <q-form @submit="editLesson">
                <div class="column" style="gap: 1.5rem">
                  <div class="row q-col-gutter-md">
                    <div class="col-12 col-md-6">
                      <BaseInput
                        v-model="lesson.subject"
                        label="Fach"
                        placeholder="Mathe, Deutsch, Biologie" />
                    </div>

                    <div class="col-12 col-md-6">
                      <BaseInput
                        v-model="lesson.thematicAreas"
                        label="Themenbereiche"
                        placeholder="Analysis, Literatur, Natur" />
                    </div>
                  </div>

                  <BaseInput
                    v-model="lesson.targetAudience"
                    label="Zielgruppe"
                    placeholder="8. Klasse" />

                  <BaseInput
                    v-model="lesson.schedule"
                    label="Turnus"
                    placeholder="jedes Semester" />

                  <BaseInput
                    v-model="lesson.priorKnowledge"
                    label="Vorwissen"
                    type="textarea" />

                  <BaseInput
                    v-model="lesson.learningPrerequisites"
                    label="Lernvoraussetzung"
                    type="textarea" />

                  <BaseInput
                    v-model="lesson.license"
                    label="Lizenz"
                    placeholder="Frei verfügbar" />

                  <div class="row">
                    <PrimaryButton
                      label="Speichern"
                      :loading="performingEditLesson"
                      class="elst__base-button-width"
                      type="submit" />
                  </div>
                </div>
              </q-form>
            </q-card-section>
          </BaseCard>

          <BaseCard title="Unterrichtseinheiten">
            <q-card-section>
              <OBaseAnimatedList :items="lesson.teachingUnits">
                <template #item="{ item }">
                  <q-item
                    clickable
                    class="bg-grey-2 elst__rounded"
                    :to="viewTeachingUnitRoute(lessonId, item.id)">
                    <q-item-section side>
                      <q-icon
                        name="mdi-drag"
                        class="drag-handle"
                        draggable="true"
                        style="cursor: grab" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>
                        {{ item.topic }}
                      </q-item-label>
                    </q-item-section>
                  </q-item>
                </template>
              </OBaseAnimatedList>

              <PrimaryButton
                label="Hinzufügen"
                icon="mdi-plus"
                flat
                text-color="primary"
                class="q-mt-md"
                @click="openCreateTeachingUnitDialog" />
            </q-card-section>
          </BaseCard>

          <BaseCard
            title="Gesamte Unterrichtsplanung löschen"
            header-color="red-1"
            header-text-color="red-8">
            <q-card-section>
              <p class="q-mb-none">
                Die Unterrichtsplanung und alle darauf aufbauenden Kurse werden
                gelöscht.
              </p>
            </q-card-section>
            <q-card-section>
              <TertiaryButton
                label="Löschen"
                hover-color="red-1"
                hover-text-color="red-10"
                :loading="performingDeleteLesson"
                class="elst__base-button-width"
                @click="openDeleteLessonDialog" />
            </q-card-section>
          </BaseCard>
        </div>

        <div class="col-auto">
          <OCoursesForLesson :lesson-id="lessonId" style="width: 300px" />
        </div>
      </div>
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import OLessonHeader from 'src/lessons/view-lesson/OLessonHeader.vue';
import { onMounted, ref } from 'vue';
import { Lesson } from 'src/services/generated/openapi';
import { withLoading } from 'src/core/useWithLoading';
import { lessonApi, teachingUnitApi } from 'src/services/lesson_planning';
import { useQuasar } from 'quasar';
import {
  confirmDialog,
  isRequired,
  stringPromptDialog,
} from 'src/core/useBaseDialog';
import { useNotifications } from 'src/core/useNotifications';
import BaseInput from 'src/core/BaseInput.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import BaseCard from 'src/core/BaseCard.vue';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import { useAppRouter } from 'src/router/useAppRouter';
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import OCoursesForLesson from 'src/lessons/view-lesson/OCoursesForLesson.vue';

const quasar = useQuasar();
const notifications = useNotifications();
const { browseLessons, viewTeachingUnitRoute } = useAppRouter();

const props = defineProps<{
  lessonId: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const lesson = ref<Lesson>();
const performingEditLesson = ref(false);
const performingDeleteLesson = ref(false);
const performingCreateTeachingUnit = ref(false);

onMounted(() => {
  withLoading(
    lessonApi
      .getLesson(props.lessonId)
      .then((response) => {
        lesson.value = response.data;
      })
      .catch((err) => {
        notifications.apiError(err);

        browseLessons();
      })
      .finally(() => {
        initialized.value = true;
      }),
    loading
  );
});

function openEditTopicDialog() {
  quasar
    .dialog(
      stringPromptDialog('Thema festlegen', 'Thema', {
        model: lesson.value?.topic,
        ok: { label: 'Speichern' },
        isValid: isRequired,
      })
    )
    .onOk((topic) => {
      lessonApi
        .editLesson(props.lessonId, {
          topic,
        })
        .then(() => {
          if (lesson.value) {
            lesson.value.topic = topic;
          }

          notifications.saved();
        })
        .catch((err) => {
          notifications.apiError(err);
        });
    });
}

function editLesson() {
  if (lesson.value) {
    withLoading(
      lessonApi
        .editLesson(props.lessonId, {
          license: lesson.value?.license,
          instructionalParameters: {
            subject: lesson.value.subject ?? '',
            targetAudience: lesson.value.targetAudience ?? '',
            schedule: lesson.value.schedule ?? '',
          },
          preparationFactors: {
            learningPrerequisites: lesson.value.learningPrerequisites ?? '',
            priorKnowledge: lesson.value.priorKnowledge ?? '',
            thematicAreas: lesson.value.thematicAreas ?? '',
          },
        })
        .then(() => {
          notifications.saved();
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingEditLesson
    );
  }
}

function openDeleteLessonDialog() {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoading(
      lessonApi
        .deleteLesson(props.lessonId)
        .then(() => {
          notifications.deleted();

          browseLessons();
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingDeleteLesson
    );
  });
}

function openCreateTeachingUnitDialog() {
  quasar
    .dialog(
      stringPromptDialog('Neue Unterrichtseinheit', 'Thema', {
        ok: {
          label: 'Erstellen',
        },
        isValid: isRequired,
      })
    )
    .onOk((topic) => {
      withLoading(
        teachingUnitApi
          .createTeachingUnit(props.lessonId, {
            topic,
          })
          .then((response) => {
            if (lesson.value) {
              lesson.value.teachingUnits.push({
                id: response.data,
                topic,
              });
            }

            notifications.created();
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingCreateTeachingUnit
      );
    });
}
</script>
