<template>
  <q-dialog
    ref="dialogRef"
    class="text-grey-10"
    no-refocus
    transition-show="fadeIn"
    transition-hide="fadeOut"
    @hide="onDialogHide">
    <q-card style="width: 800px" class="q-dialog-plugin">
      <q-form @submit="submit" @reset="onDialogCancel">
        <q-card-section class="text-body1">
          Unterrichtsphase {{ editMode ? 'bearbeiten' : 'erstellen' }}
        </q-card-section>

        <q-card-section>
          <div class="column" style="gap: 1rem">
            <div class="row">
              <div class="col">
                <BaseInput
                  v-model="topic"
                  label="Unterrichtsgeschehen"
                  placeholder="z.B. Die SuS bearbeiten Thema xy"
                  type="textarea"
                  autogrow
                  input-style="max-height: 300px"
                  :rules="[isRequired]" />
              </div>
            </div>

            <div class="row q-col-gutter-sm">
              <div class="col">
                <BaseSelect
                  v-model="phase"
                  label="Phase im Lernzyklus"
                  :options="phaseOptions"
                  :rules="[isRequired]" />
              </div>

              <div class="col">
                <BaseInput
                  v-model.number="timeFrame"
                  label="Geplante Dauer in Minuten"
                  :rules="[isGreaterThanOrEqual(0)]" />
              </div>
            </div>

            <div class="row">
              <div class="col">
                <BaseSelect
                  v-model="teacherPresence"
                  label="Lehrerpräsenz"
                  :options="teacherPresenceOptions"
                  :rules="[isRequired]" />
              </div>
            </div>
          </div>
        </q-card-section>

        <div class="overflow-hidden q-px-md q-pb-md q-mt-md">
          <div class="row reverse-wrap justify-end q-col-gutter-md">
            <div class="col-12 col-md-auto">
              <SecondaryButton
                label="Abbrechen"
                style="min-width: 150px"
                class="full-width"
                type="reset" />
            </div>

            <div class="col-12 col-md-auto">
              <PrimaryButton
                :label="editMode ? 'Speichern' : 'Erstellen'"
                style="min-width: 150px"
                class="full-width"
                type="submit" />
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
import { computed, ref } from 'vue';
import {
  CreateOrEditTeachingPhaseDialogProps,
  CreateOrEditTeachingPhaseDialogResult,
} from 'src/lessons/view-teaching-unit/useTeachingPhaseDialog';
import BaseInput from 'src/core/BaseInput.vue';
import { useRules } from 'src/core/useRules';
import {
  LearningCyclePhase,
  TeacherPresence,
} from 'src/services/generated/openapi';
import BaseSelect from 'src/core/BaseSelect.vue';
import { learningCyclePhaseLabel } from 'src/lessons/view-teaching-unit/useTeachingPhase';
import { useTeacherPresence } from 'src/lessons/view-teaching-unit/useTeacherPresence';

const { getTeacherPresenceLabel } = useTeacherPresence();
const { isRequired, isGreaterThanOrEqual } = useRules();
const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } =
  useDialogPluginComponent();

const props = defineProps<CreateOrEditTeachingPhaseDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const editMode = computed(() => props.teachingPhase !== undefined);

const topic = ref(props.teachingPhase?.topic ?? '');
const timeFrame = ref(props.teachingPhase?.timeFrame?.toString() ?? '');

const teacherPresenceOptions: { label: string; value: TeacherPresence }[] = [
  {
    label: getTeacherPresenceLabel(TeacherPresence.Absent),
    value: TeacherPresence.Absent,
  },
  {
    label: getTeacherPresenceLabel(TeacherPresence.Remote),
    value: TeacherPresence.Remote,
  },
  {
    label: getTeacherPresenceLabel(TeacherPresence.OnSite),
    value: TeacherPresence.OnSite,
  },
];
const teacherPresence = ref(
  props.teachingPhase?.teacherPresence ?? TeacherPresence.Absent
);

const phaseOptions: { label: string; value: LearningCyclePhase }[] = [
  {
    label: learningCyclePhaseLabel(LearningCyclePhase.Acquisition),
    value: LearningCyclePhase.Acquisition,
  },
  {
    label: learningCyclePhaseLabel(LearningCyclePhase.Collaboration),
    value: LearningCyclePhase.Collaboration,
  },
  {
    label: learningCyclePhaseLabel(LearningCyclePhase.Discussion),
    value: LearningCyclePhase.Discussion,
  },
  {
    label: learningCyclePhaseLabel(LearningCyclePhase.Inquiry),
    value: LearningCyclePhase.Inquiry,
  },
  {
    label: learningCyclePhaseLabel(LearningCyclePhase.Practice),
    value: LearningCyclePhase.Practice,
  },
  {
    label: learningCyclePhaseLabel(LearningCyclePhase.Production),
    value: LearningCyclePhase.Production,
  },
];
const phase = ref<LearningCyclePhase>(
  props.teachingPhase?.phase ?? LearningCyclePhase.Acquisition
);

function submit() {
  const result: CreateOrEditTeachingPhaseDialogResult = {
    topic: topic.value,
    timeFrame: timeFrame.value ? Number.parseInt(timeFrame.value) : undefined,
    phase: phase.value,
    teacherPresence: teacherPresence.value,
  };

  onDialogOK(result);
}
</script>
