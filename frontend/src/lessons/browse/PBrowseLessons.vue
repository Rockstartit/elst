<template>
  <PBase content-width="600px">
    <OLessonList>
      <template #item="{ lesson }">
        <MLessonOverview
          :lesson="lesson"
          clickable
          @click="viewLesson(lesson.id)" />
      </template>
    </OLessonList>

    <div class="row justify-center q-mt-md">
      <PrimaryButton
        label="Neuen Unterricht planen"
        :loading="performingCreateLesson"
        @click="openCreateLessonDialog" />
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { withLoading } from 'src/core/useWithLoading';
import { ref } from 'vue';
import { useAppRouter } from 'src/router/useAppRouter';
import OLessonList from 'src/lessons/browse/OLessonList.vue';
import MLessonOverview from 'src/lessons/browse/MLessonOverview.vue';
import { lessonApi } from 'src/services/lesson_planning';
import { useQuasar } from 'quasar';
import { stringPromptDialog } from 'src/core/useBaseDialog';

const quasar = useQuasar();
const { viewLesson } = useAppRouter();

const performingCreateLesson = ref(false);

function openCreateLessonDialog() {
  quasar
    .dialog(
      stringPromptDialog('Neuen Unterricht planen', 'Thema', {
        ok: {
          label: 'Erstellen',
        },
        isValid: (value) => value.length > 0,
      })
    )
    .onOk((topic) => {
      createLesson(topic);
    });
}

function createLesson(topic: string) {
  return withLoading(
    lessonApi
      .createLesson({
        topic,
      })
      .then((response) => {
        viewLesson(response.data);
      }),
    performingCreateLesson
  );
}
</script>
