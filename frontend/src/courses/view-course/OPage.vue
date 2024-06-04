<template>
  <div class="q-pa-md">
    <div class="col" style="max-width: 600px">
      <MPageHeader :title="page.title" @edit-title="openEditPageTitle" />

      <q-tabs
        v-model="tab"
        no-caps
        active-color="primary"
        align="left"
        class="bg-grey-1 elst__rounded overflow-hidden q-mt-sm">
        <q-tab :name="generalTab" class="full-width"> Allgemein </q-tab>
        <q-tab :name="mockupTab" class="full-width"> Designvorschläge </q-tab>
        <q-tab :name="buildingBlocksTab" class="full-width">
          Seitenstruktur
        </q-tab>
      </q-tabs>

      <q-tab-panels v-model="tab">
        <q-tab-panel :name="generalTab">
          <OPageNotes v-model="page" />
        </q-tab-panel>
        <q-tab-panel :name="mockupTab">
          <OPageMockups v-model="page.mockups" :page-id="page.id" />
        </q-tab-panel>
        <q-tab-panel :name="buildingBlocksTab">
          <OPageBuildingBlocks
            v-model="page.buildingBlocks"
            :page-id="page.id"
            :technology-wish="technologyWish" />
        </q-tab-panel>
      </q-tab-panels>
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
import { ref } from 'vue';

const generalTab = 'general';
const mockupTab = 'mockups';
const buildingBlocksTab = 'building_blocks';

const quasar = useQuasar();
const notifications = useNotifications();

defineProps<{
  courseId: string;
  technologyWish: string;
}>();

const tab = ref(generalTab);
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
