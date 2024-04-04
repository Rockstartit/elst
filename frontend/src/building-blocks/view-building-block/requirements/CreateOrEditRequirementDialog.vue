<template>
  <q-dialog
    ref="dialogRef"
    class="text-grey-10"
    no-refocus
    transition-show="fadeIn"
    transition-hide="fadeOut"
    @hide="onDialogHide">
    <q-card class="q-dialog-plugin">
      <q-form @submit="submit" @reset="onDialogCancel">
        <q-card-section class="text-body1">
          Anforderung dokumentieren
        </q-card-section>

        <q-card-section class="column" style="gap: 0.75rem">
          <div class="row">
            <div class="col">
              <BaseSelect v-model="type" label="Typ" :options="typeOptions" />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <BaseInput
                v-model="content"
                label="Anforderung"
                autogrow
                :rules="[isRequired]" />
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
import BaseSelect from 'src/core/BaseSelect.vue';
import { RequirementType } from 'src/services/generated/openapi/building_blocks';
import { useRules } from 'src/core/useRules';

export interface CreateOrEditRequirementDialogProps {
  initialContent?: string;
  initialType?: RequirementType;
}

export interface CreateOrEditRequirementDialogRequest {
  content: string;
  type: RequirementType;
}

const { isRequired } = useRules();
const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } =
  useDialogPluginComponent();

const props = defineProps<CreateOrEditRequirementDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const type = ref<RequirementType>(props.initialType ?? 'TEACHER');
const typeOptions: { value: RequirementType; label: string }[] = [
  {
    label: 'Lehrer',
    value: 'TEACHER',
  },
  {
    label: 'Pädagogisch',
    value: 'PEDAGOGICAL',
  },
  {
    label: 'Technologisch',
    value: 'TECHNOLOGICAL',
  },
  {
    label: 'Institutionell',
    value: 'INSTITUTIONAL',
  },
  {
    label: 'Schüler',
    value: 'LEARNER',
  },
];
const content = ref(props.initialContent ?? '');

function submit() {
  const request: CreateOrEditRequirementDialogRequest = {
    content: content.value,
    type: type.value,
  };
  onDialogOK(request);
}
</script>
