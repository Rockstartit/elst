<template>
  <PBase content-width="900px">
    <div v-if="initialized && teachingUnit">
      <OTeachingUnitHeader
        :topic="teachingUnit.topic"
        class="q-mb-lg"
        @edit-topic="openEditTopicDialog" />

      <div class="column" style="gap: 2rem">
        <BaseCard title="Allgemein">
          <q-card-section>
            <q-form @submit="editTeachingUnit">
              <div class="column" style="gap: 1.5rem">
                <BaseInput
                  v-model="teachingUnit.roughContentAnalysis"
                  label="Grobe Sachanalyse"
                  type="textarea"
                  autogrow
                  input-style="min-height: 100px; max-height: 300px" />

                <BaseInput
                  v-model="teachingUnit.curriculumAlignment"
                  label="Einordnung in Bildungsplan"
                  type="textarea"
                  autogrow
                  input-style="min-height: 100px; max-height: 300px" />

                <BaseInput
                  v-model="teachingUnit.acquiredCompetences"
                  type="textarea"
                  autogrow
                  input-style="max-height: 300px" />

                <BaseInput
                  v-model="teachingUnit.instructionMethods"
                  label="Lehrmethoden"
                  type="textarea"
                  autogrow
                  input-style="max-height: 300px" />

                <BaseInput
                  v-model="teachingUnit.frameworkConditions"
                  label="Rahmenbedingungen"
                  type="textarea"
                  autogrow
                  input-style="max-height: 300px" />

                <BaseInput
                  v-model="teachingUnit.didacticConsiderations"
                  label="Didaktische Überlegungen"
                  type="textarea"
                  autogrow
                  input-style="min-height: 100px; max-height: 300px" />

                <div class="row">
                  <PrimaryButton
                    label="Speichern"
                    :loading="performingEdit"
                    class="elst__base-button-width"
                    type="submit" />
                </div>
              </div>
            </q-form>
          </q-card-section>
        </BaseCard>

        <BaseCard title="Unterrichtsverlaufsplan">
          <q-card-section>
            <Sortable
              :list="teachingUnit.teachingPhases"
              item-key="id"
              tag="div"
              class="column"
              style="gap: 1.5rem"
              :options="sortableOptions"
              @update="onTeachingPhasesReorder">
              <template #item="{ element, index }">
                <OTeachingPhase v-model="sortedTeachingPhases[index]">
                  <template #before>
                    <q-item-section side top>
                      <q-icon
                        name="mdi-drag"
                        class="drag-handle"
                        draggable="true"
                        style="cursor: grab" />
                    </q-item-section>
                  </template>

                  <template #after>
                    <q-item-section side top>
                      <div class="row" style="gap: 0.75rem">
                        <TertiaryButton
                          icon="mdi-pencil-outline"
                          dense
                          flat
                          text-color="grey-10"
                          @click="openEditTeachingPhaseDialog(element)" />

                        <TertiaryButton
                          icon="mdi-delete-outline"
                          dense
                          flat
                          text-color="grey-10"
                          hover-color="red-1"
                          hover-text-color="red-10"
                          @click="openDeleteTeachingPhaseDialog(element)" />
                      </div>
                    </q-item-section>
                  </template>
                </OTeachingPhase>
              </template>
            </Sortable>

            <PrimaryButton
              label="Hinzufügen"
              icon="mdi-plus"
              flat
              text-color="primary"
              class="q-mt-md"
              @click="openCreateTeachingPhaseDialog" />
          </q-card-section>
        </BaseCard>

        <BaseCard
          title="Unterrichtseinheit löschen"
          header-color="red-1"
          header-text-color="red-8">
          <q-card-section>
            <p class="q-mb-none">
              Die Unterrichtseinheit mit allen hochgeladenen Lehrmaterialien
              wird gelöscht.
            </p>
          </q-card-section>
          <q-card-section>
            <TertiaryButton
              label="Löschen"
              hover-color="red-1"
              hover-text-color="red-10"
              :loading="performingDelete"
              class="elst__base-button-width"
              @click="openDeleteLessonDialog" />
          </q-card-section>
        </BaseCard>
      </div>
    </div>

    <template #breadcrumbs>
      <TheBreadcrumbs>
        <q-breadcrumbs-el
          class="cursor-pointer"
          :to="{ name: availableRoutes.view_lesson, params: { lessonId } }">
          {{ lesson?.topic }}
        </q-breadcrumbs-el>
        <q-breadcrumbs-el class="text-black">
          Unterrichtseinheiten
        </q-breadcrumbs-el>
        <q-breadcrumbs-el>
          {{ teachingUnit?.topic }}
        </q-breadcrumbs-el>
      </TheBreadcrumbs>
    </template>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { computed, onMounted, ref } from 'vue';
import {
  Lesson,
  TeachingPhase,
  TeachingUnit,
} from 'src/services/generated/openapi';
import { withLoading } from 'src/core/useWithLoading';
import {
  lessonApi,
  teachingPhaseApi,
  teachingUnitApi,
} from 'src/services/lesson_planning';
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
import OTeachingUnitHeader from 'src/lessons/view-teaching-unit/OTeachingUnitHeader.vue';
import {
  CreateOrEditTeachingPhaseDialogResult,
  createTeachingPhaseDialog,
  editTeachingPhaseDialog,
} from 'src/lessons/view-teaching-unit/useTeachingPhaseDialog';
import OTeachingPhase from 'src/lessons/view-teaching-unit/OTeachingPhase.vue';
import TheBreadcrumbs from 'src/core/TheBreadcrumbs.vue';
import { availableRoutes } from 'src/router/routes';
import { Sortable } from 'sortablejs-vue3';
import { sortableOptions } from 'src/core/useSortableList';

const quasar = useQuasar();
const notifications = useNotifications();
const { viewLesson } = useAppRouter();

const props = defineProps<{
  lessonId: string;
  teachingUnitId: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const lesson = ref<Lesson>();
const teachingUnit = ref<TeachingUnit>();
const performingEdit = ref(false);
const performingDelete = ref(false);
const performingCreateTeachingPhase = ref(false);

const sortedTeachingPhases = computed(() => {
  if (!teachingUnit.value) {
    return [];
  }

  return [...teachingUnit.value.teachingPhases].sort(
    (a, b) => a.order - b.order
  );
});

onMounted(() => {
  const teachingUnitPromise = teachingUnitApi
    .getTeachingUnit(props.teachingUnitId)
    .then((response) => {
      teachingUnit.value = response.data;
      teachingUnit.value?.teachingPhases.sort((a, b) => a.order - b.order);
    });

  const lessonPromise = lessonApi.getLesson(props.lessonId).then((response) => {
    lesson.value = response.data;
  });

  withLoading(
    Promise.allSettled([teachingUnitPromise, lessonPromise]).finally(() => {
      initialized.value = true;
    }),
    loading
  );
});

function openEditTopicDialog() {
  quasar
    .dialog(
      stringPromptDialog('Thema festlegen', 'Thema', {
        model: teachingUnit.value?.topic,
        ok: { label: 'Speichern' },
        isValid: isRequired,
      })
    )
    .onOk((topic) => {
      teachingUnitApi
        .editTeachingUnit(props.teachingUnitId, {
          topic,
        })
        .then(() => {
          if (teachingUnit.value) {
            teachingUnit.value.topic = topic;
          }

          notifications.saved();
        })
        .catch((err) => {
          notifications.apiError(err);
        });
    });
}

function editTeachingUnit() {
  if (teachingUnit.value) {
    withLoading(
      teachingUnitApi
        .editTeachingUnit(props.teachingUnitId, {
          curriculumAlignment: teachingUnit.value?.curriculumAlignment,
          acquiredCompetences: teachingUnit.value?.acquiredCompetences,
          didacticConsiderations: teachingUnit.value?.didacticConsiderations,
          frameworkConditions: teachingUnit.value?.frameworkConditions,
          instructionMethods: teachingUnit.value?.instructionMethods,
          roughContentAnalysis: teachingUnit.value?.roughContentAnalysis,
        })
        .then(() => {
          notifications.saved();
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingEdit
    );
  }
}

function openDeleteLessonDialog() {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoading(
      teachingUnitApi
        .deleteTeachingUnit(props.teachingUnitId)
        .then(() => {
          notifications.deleted();

          viewLesson(props.lessonId);
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingDelete
    );
  });
}

function openCreateTeachingPhaseDialog() {
  quasar
    .dialog(createTeachingPhaseDialog())
    .onOk((result: CreateOrEditTeachingPhaseDialogResult) => {
      withLoading(
        teachingPhaseApi
          .createTeachingPhase(props.teachingUnitId, {
            topic: result.topic,
            phase: result.phase,
            timeFrame: result.timeFrame,
          })
          .then((response) => {
            if (teachingUnit.value) {
              const maxOrder = Math.max(
                ...(teachingUnit.value?.teachingPhases.map(
                  (teachingPhase) => teachingPhase.order
                ) ?? [0])
              );

              teachingUnit.value.teachingPhases.push({
                id: response.data,
                topic: result.topic,
                phase: result.phase,
                timeFrame: result.timeFrame,
                learningMaterials: [],
                order: maxOrder + 1,
              });
            }

            notifications.created();
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingCreateTeachingPhase
      );
    });
}

function openEditTeachingPhaseDialog(teachingPhase: TeachingPhase) {
  quasar
    .dialog(
      editTeachingPhaseDialog({
        teachingPhase,
      })
    )
    .onOk((result: CreateOrEditTeachingPhaseDialogResult) => {
      withLoading(
        teachingPhaseApi
          .editTeachingPhase(teachingPhase.id, {
            topic: result.topic,
            phase: result.phase,
            timeFrame: result.timeFrame,
          })
          .then(() => {
            teachingPhase.topic = result.topic;
            teachingPhase.timeFrame = result.timeFrame;
            teachingPhase.phase = result.phase;

            notifications.saved();
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingCreateTeachingPhase
      );
    });
}

function openDeleteTeachingPhaseDialog(teachingPhase: TeachingPhase) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoading(
      teachingPhaseApi
        .deleteTeachingPhase(teachingPhase.id)
        .then(() => {
          notifications.deleted();

          if (teachingUnit.value) {
            const index =
              teachingUnit.value.teachingPhases.indexOf(teachingPhase);

            if (index >= 0) {
              teachingUnit.value.teachingPhases.splice(index, 1);
            }
          }
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingDelete
    );
  });
}

function onTeachingPhasesReorder(event: {
  oldIndex: number;
  newIndex: number;
}) {
  const length = teachingUnit.value?.teachingPhases.length ?? 0;

  if (
    teachingUnit.value &&
    event.oldIndex < length &&
    event.newIndex < length
  ) {
    const element = teachingUnit.value.teachingPhases.splice(
      event.oldIndex,
      1
    )[0];
    teachingUnit.value.teachingPhases.splice(event.newIndex, 0, element);

    teachingPhaseApi
      .reorderTeachingPhases(
        props.teachingUnitId,
        teachingUnit.value.teachingPhases.map(
          (teachingPhase) => teachingPhase.id
        )
      )
      .then(() => {
        notifications.success();

        teachingUnit.value?.teachingPhases.forEach(
          (teachingPhase, index) => (teachingPhase.order = index)
        );
      })
      .catch((err) => {
        notifications.apiError(err);

        teachingUnit.value?.teachingPhases.sort((a, b) => a.order - b.order);
      });
  }
}
</script>
