<template>
  <div class="col">
    <div class="row">
      <div class="col">
        <div class="row text-h6 items-center" style="gap: 0.5rem">
          <span class="text-weight-regular">E-Learning Kurs für </span>
          <span> {{ topic }} </span>

          <TertiaryButton
            icon="mdi-open-in-new"
            dense
            flat
            :to="viewLessonRoute(lessonId)" />
        </div>
      </div>

      <div class="col-auto">
        <q-chip
          color="primary"
          text-color="white"
          rounded
          clickable
          class="shadow-0"
          @click="$emit('edit-technology-wish')">
          <div class="row text-body1 items-center q-px-lg" style="gap: 0.5rem">
            <q-icon name="mdi-code-tags" size="20px" />

            <span>
              {{ technologyWish }}
            </span>
          </div>
        </q-chip>
      </div>
    </div>

    <q-separator class="q-mt-sm" />
  </div>
</template>

<script lang="ts" setup>
import TertiaryButton from 'src/core/TertiaryButton.vue';
import { useAppRouter } from 'src/router/useAppRouter';

const { viewLessonRoute } = useAppRouter();

defineProps<{
  lessonId: string;
  topic: string;
  technologyWish: string;
}>();

defineEmits(['edit-technology-wish']);
</script>
