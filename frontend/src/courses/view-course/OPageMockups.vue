<template>
  <div>
    <div class="row">
      <q-list class="column col" style="gap: 0.5rem">
        <MMockup v-for="mockup in mockups" :key="mockup.id" :mockup="mockup">
          <template #after>
            <q-item-section side>
              <div class="row" style="gap: 0.5rem">
                <PrimaryButton
                  icon="mdi-download-outline"
                  text-color="primary"
                  dense
                  flat
                  @click="downloadFile(mockup.fileId)" />

                <TertiaryButton
                  icon="mdi-delete-outline"
                  dense
                  flat
                  hover-color="red-1"
                  hover-text-color="red-10"
                  :loading="performingDeleteMockup.includes(mockup.id)"
                  @click="openDeleteMockupDialog(mockup)" />
              </div>
            </q-item-section>
          </template>
        </MMockup>

        <div
          class="row"
          :class="{
            'q-py-md': mockups.length > 0,
          }">
          <BaseUploader
            multiple
            :factory="mockupUploadFactory"
            color="transparent"
            text-color="grey-8"
            class="full-width" />
        </div>
      </q-list>
    </div>
  </div>
</template>

<script lang="ts" setup>
import MMockup from 'src/courses/view-course/MMockup.vue';
import BaseUploader from 'src/core/BaseUploader.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import { Mockup } from 'src/services/generated/openapi';
import { useContentDownload } from 'src/core/useContentDownload';
import { useNotifications } from 'src/core/useNotifications';
import { confirmDialog } from 'src/core/useBaseDialog';
import { withLoadingArray } from 'src/core/useWithLoading';
import { pageMockupApi } from 'src/services/course_conceptualization';
import { QUploaderFactoryObject, useQuasar } from 'quasar';
import { basePath } from 'boot/axios';
import { ref } from 'vue';
import { useAuthenticationStore } from 'stores/authentication/store';

const quasar = useQuasar();
const notifications = useNotifications();
const authStore = useAuthenticationStore();
const { downloadFile } = useContentDownload();

const props = defineProps<{
  pageId: string;
}>();

const mockups = defineModel<Mockup[]>({ required: true });

const performingDeleteMockup = ref<string[]>([]);

function openDeleteMockupDialog(mockup: Mockup) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      pageMockupApi
        .deleteMockup(mockup.id)
        .then(() => {
          const index = mockups.value.indexOf(mockup);

          if (index >= 0) {
            mockups.value.splice(index, 1);
          }

          notifications.deleted();
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingDeleteMockup,
      mockup.id
    );
  });
}

function mockupUploadFactory(
  files: readonly File[]
): Promise<QUploaderFactoryObject> {
  return authStore.getAccessToken().then((accessToken) => {
    return {
      url: `${basePath}/pages/${props.pageId}/mockups`,
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
</script>
