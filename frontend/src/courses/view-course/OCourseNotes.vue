<template>
  <div>
    <p class="text-body2 text-weight-medium"> Umsetzungshinweise </p>

    <AToggleInput>
      <template #input="{ hideInput }">
        <BaseInput
          v-model="notes"
          type="textarea"
          autogrow
          placeholder="Hier können Hinweise zur Umsetzung des E-Learning Kurses dokumentiert werden."
          hint="Umsetzungshinweise für Entwickler"
          input-style="min-height: 100px; max-height: 300px" />

        <PrimaryButton
          v-if="canSaveNotes"
          label="Speichern"
          :loading="performingSaveNotes"
          class="q-mt-md elst__base-button-width"
          @click="saveNotes(hideInput)" />
      </template>

      <template #display>
        <p class="text-body2 preserve-line-breaks">
          {{ notes }}
        </p>
      </template>
    </AToggleInput>
  </div>
</template>

<script lang="ts" setup>
import BaseInput from 'src/core/BaseInput.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import AToggleInput from 'src/core/AToggleInput.vue';
import { computed, onMounted, ref } from 'vue';
import { withLoading } from 'src/core/useWithLoading';
import { courseApi } from 'src/services/course_conceptualization';
import { useNotifications } from 'src/core/useNotifications';

const notifications = useNotifications();

const props = defineProps<{
  courseId: string;
}>();

const notes = defineModel<string>();

const lastSavedNotes = ref<string>();
const performingSaveNotes = ref(false);
const canSaveNotes = computed(() => notes.value !== lastSavedNotes.value);

onMounted(() => {
  lastSavedNotes.value = notes.value;
});

function saveNotes(hideInput?: () => void) {
  return withLoading(
    courseApi
      .editCourse(props.courseId, {
        notes: notes.value,
      })
      .then(() => {
        lastSavedNotes.value = notes.value;
        notifications.saved();

        if (hideInput) {
          hideInput();
        }
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingSaveNotes
  );
}
</script>
