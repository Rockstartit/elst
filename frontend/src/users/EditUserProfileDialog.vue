<template>
  <q-dialog
    ref="dialogRef"
    class="text-grey-10"
    no-refocus
    transition-show="fadeIn"
    transition-hide="fadeOut"
    persistent
    @hide="onDialogHide">
    <q-card style="width: 600px" class="q-dialog-plugin">
      <q-form @submit="submit" @reset="onDialogCancel">
        <q-card-section class="text-body1">
          Profil aktualisieren
        </q-card-section>

        <q-card-section>
          <div class="column" style="gap: 1rem">
            <div class="row q-col-gutter-sm">
              <div class="col">
                <BaseInput v-model="firstName" label="Vorname" />
              </div>

              <div class="col">
                <BaseInput v-model="lastName" label="Nachname" />
              </div>
            </div>
          </div>
        </q-card-section>

        <div class="overflow-hidden q-px-md q-pb-md q-mt-md">
          <div class="row justify-end">
            <PrimaryButton
              label="Speichern"
              style="min-width: 150px"
              type="submit"
              class="elst__base-button-width" />
          </div>
        </div>
      </q-form>
    </q-card>
  </q-dialog>
</template>

<script lang="ts" setup>
import { useDialogPluginComponent } from 'quasar';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import BaseInput from 'src/core/BaseInput.vue';
import { ref } from 'vue';
import { EditUserProfileDialogProps } from 'src/users/useEditUserProfileDialog';
import { userApi } from 'src/services';
import { useNotifications } from 'src/core/useNotifications';

const notifications = useNotifications();
const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } =
  useDialogPluginComponent();

const props = defineProps<EditUserProfileDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const firstName = ref(props.userProfile.firstName ?? '');
const lastName = ref(props.userProfile.lastName ?? '');

function submit() {
  userApi
    .editUserProfile(props.userProfile.id, {
      firstName: firstName.value,
      lastName: lastName.value,
    })
    .then(() => {
      const result = {
        id: props.userProfile.id,
        firstName: firstName.value,
        lastName: lastName.value,
      };

      notifications.saved();

      onDialogOK(result);
    })
    .catch((err) => {
      notifications.apiError(err);
    });
}
</script>
