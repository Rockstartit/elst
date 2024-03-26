<template>
  <q-dialog
    ref="dialogRef"
    class="text-grey-10"
    no-refocus
    transition-show="fadeIn"
    transition-hide="fadeOut"
    @hide="onDialogHide">
    <q-card style="width: 400px" class="q-dialog-plugin">
      <q-form @submit="submit" @reset="onDialogCancel">
        <q-card-section class="text-body1">
          Kursdetails bearbeiten
        </q-card-section>

        <q-card-section class="column" style="gap: 0.75rem">
          <div class="row">
            <div class="col">
              <BaseInput v-model="degreeState" label="Abschluss" />
              <BaseCheckbox
                v-model="gradRequiredState"
                label="Abschluss erforderlich"
                class="q-mt-xs" />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <BaseInput v-model="creditPointsState" label="Leistungspunkte" />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <BaseInput v-model="semesterState" label="Semester" />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <BaseInput v-model="scheduleState" label="Zeitplan" />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <BaseInput v-model="knowledgeState" label="Vorwissen" />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <BaseInput v-model="skillsState" label="Skills" />
            </div>
          </div>
        </q-card-section>

        <div class="overflow-hidden q-px-md q-pb-md q-mt-md">
          <div class="row reverse-wrap justify-end q-col-gutter-md">
            <div class="col-12 col-md-auto">
              <SecondaryButton
                type="tertiary"
                label="Abbrechen"
                style="min-width: 150px"
                class="full-width"
                native-type="reset" />
            </div>

            <div class="col-12 col-md-auto">
              <PrimaryButton
                type="primary"
                label="Speichern"
                style="min-width: 150px"
                class="full-width"
                native-type="submit" />
            </div>
          </div>
        </div>
      </q-form>
    </q-card>
  </q-dialog>
</template>

<script lang="ts" setup>
import { useDialogPluginComponent } from 'quasar';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import SecondaryButton from 'src/core/SecondaryButton.vue';
import BaseInput from 'src/core/BaseInput.vue';
import { ref } from 'vue';
import BaseCheckbox from 'src/core/BaseCheckbox.vue';

export interface EditCourseDetailDialogProps {
  gradRequired?: boolean;
  degree?: string;
  creditPoints?: string;
  semester?: string;
  schedule?: string;
  knowledge?: string;
  skills?: string;
}

const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } =
  useDialogPluginComponent();

const props = defineProps<EditCourseDetailDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const gradRequiredState = ref(props.gradRequired);
const degreeState = ref(props.degree);
const creditPointsState = ref(props.creditPoints);
const semesterState = ref(props.semester);
const scheduleState = ref(props.schedule);
const knowledgeState = ref(props.knowledge);
const skillsState = ref(props.skills);

function submit() {
  const payload: EditCourseDetailDialogProps = {
    gradRequired: gradRequiredState.value,
    degree: degreeState.value,
    schedule: scheduleState.value,
    creditPoints: creditPointsState.value,
    semester: semesterState.value,
    skills: skillsState.value,
    knowledge: knowledgeState.value,
  };

  onDialogOK(payload);
}
</script>
