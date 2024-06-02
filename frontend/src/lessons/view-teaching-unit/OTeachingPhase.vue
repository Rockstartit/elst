<template>
  <div class="bg-grey-2 elst__rounded overflow-hidden">
    <q-item class="bg-grey-3">
      <slot name="before" />

      <q-item-section side top>
        <q-badge
          v-if="teachingPhase.phase"
          :color="learningCyclePhaseColor(teachingPhase.phase)"
          rounded
          class="justify-center text-grey-10 q-pa-xs text-weight-medium"
          style="width: 150px">
          {{ learningCyclePhaseLabel(teachingPhase.phase) }}
        </q-badge>

        <q-item-label caption class="q-mt-xs">
          Geplante Dauer:
          <span class="text-caption text-weight-medium">
            {{
              teachingPhase.timeFrame
                ? teachingPhase.timeFrame + ' Minuten'
                : 'Keine'
            }}
          </span>
        </q-item-label>
      </q-item-section>

      <q-item-section top>
        <q-item-label class="preserve-line-breaks">
          {{ teachingPhase.topic }}
        </q-item-label>
      </q-item-section>

      <slot name="after" />
    </q-item>

    <q-list class="q-pa-md column" style="gap: 0.75rem">
      <span
        v-if="teachingPhase.learningMaterials.length === 0"
        class="text-grey-7">
        Es wurden noch keine Lernmaterialien bereitgestellt.
      </span>

      <MLearningMaterial
        v-for="learningMaterial in teachingPhase.learningMaterials"
        :key="learningMaterial.id"
        :learning-material="learningMaterial">
        <template #after>
          <q-item-section side>
            <div class="row" style="gap: 0.75rem">
              <PrimaryButton
                icon="mdi-download-outline"
                text-color="primary"
                dense
                flat
                @click="downloadFile(learningMaterial.fileId)" />

              <TertiaryButton
                icon="mdi-delete-outline"
                dense
                flat
                hover-color="red-1"
                hover-text-color="red-10"
                :loading="
                  performingDeleteLearningMaterial.includes(learningMaterial.id)
                "
                @click="openDeleteLearningMaterialDialog(learningMaterial)" />
            </div>
          </q-item-section>
        </template>
      </MLearningMaterial>
    </q-list>

    <div class="row q-px-md q-pb-md">
      <BaseUploader
        label="Lehrmaterial hochladen"
        multiple
        :factory="learningMaterialUploadFactory"
        color="transparent"
        text-color="grey-8" />
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  LearningMaterial,
  TeachingPhase,
} from 'src/services/generated/openapi';
import {
  learningCyclePhaseColor,
  learningCyclePhaseLabel,
} from './useTeachingPhase';
import BaseUploader from 'src/core/BaseUploader.vue';
import { basePath } from 'boot/axios';
import { useAuthenticationStore } from 'stores/authentication/store';
import { QUploaderFactoryObject, useQuasar } from 'quasar';
import MLearningMaterial from 'src/lessons/view-teaching-unit/MLearningMaterial.vue';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { confirmDialog } from 'src/core/useBaseDialog';
import { learningMaterialApi } from 'src/services/lesson_planning';
import { useNotifications } from 'src/core/useNotifications';
import { ref } from 'vue';
import { withLoadingArray } from 'src/core/useWithLoading';
import { useContentDownload } from 'src/core/useContentDownload';

const quasar = useQuasar();
const notifications = useNotifications();
const authStore = useAuthenticationStore();
const { downloadFile } = useContentDownload();

const teachingPhase = defineModel<TeachingPhase>({ required: true });

const performingDeleteLearningMaterial = ref<string[]>([]);

function learningMaterialUploadFactory(
  files: readonly File[]
): Promise<QUploaderFactoryObject> {
  return authStore.getAccessToken().then((accessToken) => {
    return {
      url: `${basePath}/teaching-phases/${teachingPhase.value.id}/learning-materials`,
      headers: [
        {
          name: 'Authorization',
          value: 'Bearer ' + accessToken,
        },
      ],
      formFields: files.map((file) => {
        return {
          name: 'name',
          value: file.name,
        };
      }),
    };
  });
}

function openDeleteLearningMaterialDialog(learningMaterial: LearningMaterial) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      learningMaterialApi
        .deleteLearningMaterial(learningMaterial.id)
        .then(() => {
          const index =
            teachingPhase.value.learningMaterials.indexOf(learningMaterial);

          if (index >= 0) {
            teachingPhase.value.learningMaterials.splice(index, 1);
          }

          notifications.deleted();
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingDeleteLearningMaterial,
      learningMaterial.id
    );
  });
}
</script>
