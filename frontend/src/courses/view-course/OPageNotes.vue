<template>
  <div>
    <p class="text-body2 text-weight-medium"> Umsetzungshinweise </p>
    <AToggleInput class="full-width">
      <template #display>
        <p v-if="page.notes" class="text-body2 preserve-line-breaks">
          {{ page.notes }}
        </p>
        <p v-else class="text-body2 text-grey-7"> Keine Angabe </p>
      </template>

      <template #input="{ hideInput }">
        <BaseInput
          v-model="page.notes"
          label="Hinweise"
          type="textarea"
          class="full-width" />

        <PrimaryButton
          label="Speichern"
          :loading="performingSavePageNotes"
          class="q-mt-md elst__base-button-width"
          @click="savePageNotes(hideInput)" />
      </template>
    </AToggleInput>
  </div>
</template>

<script lang="ts" setup>
import BaseInput from 'src/core/BaseInput.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import AToggleInput from 'src/core/AToggleInput.vue';
import { Page } from 'src/services/generated/openapi';
import { ref } from 'vue';
import { withLoading } from 'src/core/useWithLoading';
import { pageApi } from 'src/services/course_conceptualization';
import { useNotifications } from 'src/core/useNotifications';

const notifications = useNotifications();

const page = defineModel<Page>({ required: true });

const performingSavePageNotes = ref(false);

function savePageNotes(hideInput?: () => void) {
  return withLoading(
    pageApi
      .editPage(page.value.id, {
        notes: page.value.notes,
      })
      .then(() => {
        notifications.saved();

        if (hideInput) {
          hideInput();
        }
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingSavePageNotes
  );
}
</script>
