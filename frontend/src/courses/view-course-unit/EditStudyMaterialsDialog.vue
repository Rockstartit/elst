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
          Lernmaterialien bearbeiten
        </q-card-section>

        <q-card-section class="column" style="gap: 0.75rem">
          <div class="row">
            <div class="col">
              <BaseInput v-model="eReadingState" label="E-Reading" />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <BaseInput v-model="eBookState" label="E-Books" />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <BaseInput v-model="bibliographyState" label="Literatur" />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <BaseInput v-model="relatedLinksState" label="Verwandte Links" />
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

export interface EditStudyMaterialsDialogProps {
  eReading?: string;
  eBook?: string;
  bibliography?: string;
  relatedLinks?: string;
}

const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } =
  useDialogPluginComponent();

const props = defineProps<EditStudyMaterialsDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const eReadingState = ref(props.eReading);
const eBookState = ref(props.eBook);
const bibliographyState = ref(props.bibliography);
const relatedLinksState = ref(props.relatedLinks);

function submit() {
  const payload: EditStudyMaterialsDialogProps = {
    eReading: eReadingState.value,
    eBook: eBookState.value,
    bibliography: bibliographyState.value,
    relatedLinks: relatedLinksState.value,
  };

  onDialogOK(payload);
}
</script>
