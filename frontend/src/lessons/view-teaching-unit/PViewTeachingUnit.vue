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
                  type="textarea" />

                <BaseInput
                  v-model="teachingUnit.curriculumAlignment"
                  label="Einordnung in Bildungsplan"
                  type="textarea" />

                <BaseInput
                  v-model="teachingUnit.acquiredCompetences"
                  label="Erlernte Kompetenzen" />

                <BaseInput
                  v-model="teachingUnit.instructionMethods"
                  label="Lehrmethoden" />

                <BaseInput
                  v-model="teachingUnit.frameworkConditions"
                  label="Rahmenbedingungen" />

                <BaseInput
                  v-model="teachingUnit.didacticConsiderations"
                  label="Didaktische Überlegungen"
                  type="textarea" />

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
            <OBaseAnimatedList :items="teachingUnit.teachingPhases">
              <template #item="{ item }">
                <q-item clickable class="bg-grey-2 elst__rounded">
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
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { onMounted, ref } from 'vue';
import { TeachingUnit } from 'src/services/generated/openapi';
import { withLoading } from 'src/core/useWithLoading';
import {
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
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import OTeachingUnitHeader from 'src/lessons/view-teaching-unit/OTeachingUnitHeader.vue';

const quasar = useQuasar();
const notifications = useNotifications();
const { viewLesson } = useAppRouter();

const props = defineProps<{
  lessonId: string;
  teachingUnitId: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const teachingUnit = ref<TeachingUnit>();
const performingEdit = ref(false);
const performingDelete = ref(false);
const performingCreateTeachingPhase = ref(false);

onMounted(() => {
  withLoading(
    teachingUnitApi
      .getTeachingUnit(props.teachingUnitId)
      .then((response) => {
        teachingUnit.value = response.data;
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
    .dialog(
      stringPromptDialog('Neue Unterrichtsphase', 'Thema', {
        ok: {
          label: 'Erstellen',
        },
        isValid: isRequired,
      })
    )
    .onOk((topic) => {
      withLoading(
        teachingPhaseApi
          .createTeachingPhase(props.teachingUnitId, {
            topic,
          })
          .then((response) => {
            if (teachingUnit.value) {
              teachingUnit.value.teachingPhases.push({
                id: response.data,
                topic,
                learningMaterials: [],
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
</script>
