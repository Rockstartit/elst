<template>
  <div>
    <q-item class="q-py-md q-mb-md elst__rounded elst__bordered">
      <q-item-section side top>
        <q-badge
          v-if="teachingPhase.phase"
          :color="learningCyclePhaseColor(teachingPhase.phase)"
          rounded
          class="justify-center text-grey-10 q-pa-xs text-weight-medium"
          style="width: 150px">
          {{ learningCyclePhaseLabel(teachingPhase.phase) }}
        </q-badge>
      </q-item-section>

      <q-item-section>
        <q-item-label>
          {{ teachingPhase.topic }}
        </q-item-label>
        <q-item-label caption class="text-body2">
          Geplante Dauer:
          <span class="text-weight-medium">
            {{
              teachingPhase.timeFrame
                ? teachingPhase.timeFrame + ' Minuten'
                : 'Keine'
            }}
          </span>
        </q-item-label>
      </q-item-section>

      <q-item-section v-if="teachingPhase.learningMaterials.length > 0" side>
        <TertiaryButton
          icon="mdi-file-outline"
          icon-right="mdi-chevron-down"
          :label="
            'Lernmaterial (' + teachingPhase.learningMaterials.length + ')'
          "
          flat
          text-color="grey-10">
          <q-menu style="width: 250px">
            <q-list class="column" style="gap: 0.5rem">
              <q-item
                v-for="learningMaterial in teachingPhase.learningMaterials"
                :key="learningMaterial.id"
                clickable
                @click="downloadFile(learningMaterial.fileId)">
                <q-item-section side>
                  <q-icon name="mdi-file-outline" />
                </q-item-section>
                <q-item-section>
                  <q-item-label class="text-weight-medium">
                    {{ learningMaterial.name }}
                  </q-item-label>
                </q-item-section>

                <q-item-section side>
                  <q-icon name="mdi-download-outline" color="primary" />
                </q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </TertiaryButton>
      </q-item-section>
    </q-item>

    <div class="q-px-lg">
      <p class="text-weight-medium text-body1 q-mb-sm q-px-md"> Seiten </p>

      <div class="overflow-hidden q-px-md">
        <div class="column" style="gap: 1rem">
          <OPage
            v-for="(page, index) in teachingPhase.pages"
            :key="page.id"
            v-model="teachingPhase.pages[index]"
            :course-id="courseId"
            :loading-delete="performingDeletePage.includes(page.id)"
            @delete="openDeletePageDialog(page)" />

          <div class="row">
            <PrimaryButton
              icon="mdi-plus"
              label="Neue Seite"
              flat
              text-color="primary"
              :loading="performingCreatePage"
              @click="openCreatePageDialog" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { CourseTeachingPhase, Page } from 'src/services/generated/openapi';
import OPage from 'src/courses/view-course/OPage.vue';
import {
  learningCyclePhaseColor,
  learningCyclePhaseLabel,
} from 'src/lessons/view-teaching-unit/useTeachingPhase';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import { useContentDownload } from 'src/core/useContentDownload';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useQuasar } from 'quasar';
import { useNotifications } from 'src/core/useNotifications';
import {
  confirmDialog,
  isRequired,
  stringPromptDialog,
} from 'src/core/useBaseDialog';
import { ref } from 'vue';
import { withLoading, withLoadingArray } from 'src/core/useWithLoading';
import { pageApi } from 'src/services/course_conceptualization';

const quasar = useQuasar();
const notifications = useNotifications();
const { downloadFile } = useContentDownload();

const props = defineProps<{
  courseId: string;
}>();

const teachingPhase = defineModel<CourseTeachingPhase>({ required: true });

const performingCreatePage = ref(false);
const performingDeletePage = ref<string[]>([]);

function openCreatePageDialog() {
  quasar
    .dialog(
      stringPromptDialog('Neue Seite', 'Titel', {
        ok: {
          label: 'Erstellen',
        },
        isValid: isRequired,
      })
    )
    .onOk((title) => {
      withLoading(
        pageApi
          .createPage(props.courseId, {
            teachingPhaseId: teachingPhase.value.id,
            title,
          })
          .then((response) => {
            notifications.created();

            teachingPhase.value.pages.push({
              id: response.data,
              teachingPhaseId: teachingPhase.value.id,
              title,
              linkedPages: [],
              buildingBlocks: [],
              mockups: [],
            });
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingCreatePage
      );
    });
}

function openDeletePageDialog(page: Page) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      pageApi
        .deletePage(page.id)
        .then(() => {
          notifications.deleted();

          const index = teachingPhase.value.pages.indexOf(page);

          if (index >= 0) {
            teachingPhase.value.pages.splice(index, 1);
          }
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingDeletePage,
      page.id
    );
  });
}
</script>
