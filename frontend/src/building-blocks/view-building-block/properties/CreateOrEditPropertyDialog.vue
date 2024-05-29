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
            <div class="row q-col-gutter-sm">
              <div class="col">
                <BaseInput
                  v-model="displayName"
                  label="Anzeigename"
                  :rules="[isRequired]" />
              </div>

              <div v-if="!editMode" class="col">
                <BaseInput
                  v-model="key"
                  label="Eindeutiger Parametername"
                  :rules="[isRequired]" />
              </div>
            </div>

            <div class="row">
              <div class="col">
                <BaseInput
                  v-model="description"
                  label="Beschreibung"
                  type="textarea" />
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
import BaseInput from 'src/core/BaseInput.vue';
import { useRules } from 'src/core/useRules';
import {
  CreateOrEditPropertyDialogProps,
  CreateOrEditPropertyDialogResult,
} from 'src/building-blocks/view-building-block/properties/useCreateOrEditPropertyDialog';

const { isRequired } = useRules();
const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } =
  useDialogPluginComponent();

const props = defineProps<CreateOrEditPropertyDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const editMode = computed(() => props.property !== undefined);

const key = ref(props.property?.key ?? '');
const displayName = ref(props.property?.displayName ?? '');
const description = ref(props.property?.description ?? '');

function submit() {
  const result: CreateOrEditPropertyDialogResult = {
    key: key.value,
    displayName: displayName.value,
    description: description.value,
  };

  onDialogOK(result);
}
</script>
