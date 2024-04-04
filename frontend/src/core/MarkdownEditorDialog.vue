<template>
  <q-dialog
    ref="dialogRef"
    class="text-grey-10"
    no-refocus
    full-height
    full-width
    transition-show="fadeIn"
    transition-hide="fadeOut"
    @hide="onDialogHide">
    <q-card style="width: 800px" class="q-dialog-plugin">
      <q-form @submit="submit" @reset="onDialogCancel">
        <q-card-section class="text-body1">
          Markdown bearbeiten
        </q-card-section>

        <q-card-section class="col">
          <BaseInput
            v-model="content"
            type="textarea"
            input-style="height: 70dvh" />
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

export interface MarkdownEditorDialogProps {
  initialContent?: string;
}

const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } =
  useDialogPluginComponent();

const props = defineProps<MarkdownEditorDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const content = ref(props.initialContent ?? '');

function submit() {
  onDialogOK(content.value);
}
</script>
