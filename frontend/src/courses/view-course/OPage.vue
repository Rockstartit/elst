<template>
  <div class="q-pa-md">
    <div class="col" style="max-width: 600px">
      <MPageHeader :title="page.title" @edit-title="openEditPageTitle" />

      <OPageNotes v-model="page" class="q-mt-md" />

      <OPageMockups v-model="page.mockups" :page-id="page.id" class="q-mt-lg" />

      <OPageBuildingBlocks
        v-model="page.buildingBlocks"
        :page-id="page.id"
        :technology-wish="technologyWish"
        class="q-mt-lg" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Page } from 'src/services/generated/openapi';
import { isRequired, stringPromptDialog } from 'src/core/useBaseDialog';
import { pageApi } from 'src/services/course_conceptualization';
import { useQuasar } from 'quasar';
import { useNotifications } from 'src/core/useNotifications';
import MPageHeader from 'src/courses/view-course/MPageHeader.vue';
import OPageNotes from 'src/courses/view-course/OPageNotes.vue';
import OPageMockups from 'src/courses/view-course/OPageMockups.vue';
import OPageBuildingBlocks from 'src/courses/view-course/OPageBuildingBlocks.vue';

const quasar = useQuasar();
const notifications = useNotifications();

defineProps<{
  courseId: string;
  technologyWish: string;
}>();

const page = defineModel<Page>({ required: true });

function openEditPageTitle() {
  quasar
    .dialog(
      stringPromptDialog('Titel bearbeiten', 'Titel', {
        ok: {
          label: 'Speichern',
        },
        isValid: isRequired,
        model: page.value.title,
      })
    )
    .onOk((payload) => {
      pageApi
        .editPage(page.value.id, {
          title: payload,
        })
        .then(() => {
          notifications.saved();

          page.value.title = payload;
        })
        .catch((err) => {
          notifications.apiError(err);
        });
    });
}
</script>
