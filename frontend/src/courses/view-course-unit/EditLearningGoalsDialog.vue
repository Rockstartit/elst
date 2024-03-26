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
          Lernziele bearbeiten
        </q-card-section>

        <q-card-section>
          <div class="column" style="gap: 0.75rem">
            <div
              v-for="(goal, index) in learningGoalsState"
              :key="'goal-' + index"
              class="row">
              <q-item-section class="q-px-none">
                <BaseInput v-model="learningGoalsState[index]" autofocus />
              </q-item-section>
              <q-item-section side>
                <SecondaryButton
                  icon="mdi-close"
                  dense
                  flat
                  @click="removeLearningGoal(index)" />
              </q-item-section>
            </div>
          </div>

          <PrimaryButton
            label="Hinzufügen"
            icon="mdi-plus"
            text-color="primary"
            class="text-caption q-mt-md"
            flat
            @click="addLearningGoal" />
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
import { ref } from 'vue';
import BaseInput from 'src/core/BaseInput.vue';

export interface EditLearningGoalsDialogProps {
  learningGoals: string[];
}

const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } =
  useDialogPluginComponent();

const props = defineProps<EditLearningGoalsDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const learningGoalsState = ref([...props.learningGoals]);

function addLearningGoal() {
  learningGoalsState.value.push('');
}

function removeLearningGoal(index: number) {
  learningGoalsState.value.splice(index, 1);
}

function submit() {
  const payload: EditLearningGoalsDialogProps = {
    learningGoals: learningGoalsState.value,
  };

  onDialogOK(payload);
}
</script>
