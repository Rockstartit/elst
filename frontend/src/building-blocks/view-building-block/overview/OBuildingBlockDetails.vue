<template>
  <q-list dense>
    <q-item>
      <q-item-section>
        <q-item-label class="text-weight-medium text-body2">
          Allgemein
        </q-item-label>
      </q-item-section>
      <q-item-section side>
        <SecondaryButton
          icon="mdi-cog-outline"
          dense
          flat
          size="sm"
          @click="$emit('edit')" />
      </q-item-section>
    </q-item>

    <div class="text-body2 q-px-md q-mt-sm">
      <p v-if="buildingBlock.description" class="q-mb-none">
        {{ buildingBlock.description }}
      </p>

      <p v-else class="text-grey-7 q-mb-none"> Keine Beschreibung </p>

      <PrimaryButton
        v-if="buildingBlock.releaseStatus === 'IN_DEVELOPMENT'"
        icon="mdi-rocket-launch-outline"
        label="Veröffentlichen"
        class="q-mt-md"
        @click="releaseBuildingBlock" />

      <p
        v-if="buildingBlock.releaseStatus === 'RELEASED'"
        class="q-mb-none q-mt-md text-body1 text-green-10 text-weight-medium">
        Veröffentlicht!
      </p>
    </div>

    <q-separator class="q-my-md" />

    <div class="q-px-md">
      <p class="text-body2 text-weight-medium"> Mockups </p>
      <q-list class="column" style="gap: 0.5rem">
        <MMockup v-for="mockup in mockups" :key="mockup.id" :mockup="mockup">
          <template #after>
            <q-item-section side>
              <div class="row" style="gap: 0.75rem">
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
            class="bg-grey-2 full-width" />
        </div>
      </q-list>
    </div>

    <q-separator class="q-my-md" />

    <MContributors label="Lehrer" />

    <MContributors label="Entwickler" />
  </q-list>
</template>

<script lang="ts" setup>
import SecondaryButton from 'src/core/SecondaryButton.vue';
import MContributors from 'src/building-blocks/view-building-block/overview/MContributors.vue';
import { BuildingBlock, Mockup } from 'src/services/generated/openapi';
import MMockup from 'src/courses/view-course/MMockup.vue';
import BaseUploader from 'src/core/BaseUploader.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import { confirmDialog } from 'src/core/useBaseDialog';
import { withLoading, withLoadingArray } from 'src/core/useWithLoading';
import { QUploaderFactoryObject, useQuasar } from 'quasar';
import { onMounted, ref } from 'vue';
import { useNotifications } from 'src/core/useNotifications';
import { useContentDownload } from 'src/core/useContentDownload';
import { basePath } from 'boot/axios';
import { useAuthenticationStore } from 'stores/authentication/store';
import {
  buildingBlockApi,
  buildingBlockMockupApi,
} from 'src/services/building_blocks';

const quasar = useQuasar();
const notifications = useNotifications();
const authStore = useAuthenticationStore();
const { downloadFile } = useContentDownload();

const props = defineProps<{
  buildingBlock: BuildingBlock;
}>();

const emit = defineEmits(['edit', 'release']);

const mockups = ref<Mockup[]>([]);

const performingDeleteMockup = ref<string[]>([]);
const performingRelease = ref(false);

onMounted(() => {
  buildingBlockMockupApi
    .getAllMockups(props.buildingBlock.id)
    .then((response) => {
      mockups.value = response.data;
    })
    .catch((err) => {
      notifications.apiError(err);
    });
});

function openDeleteMockupDialog(mockup: Mockup) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      buildingBlockMockupApi
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
      url: `${basePath}/building-blocks/${props.buildingBlock.id}/mockups`,
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

function releaseBuildingBlock() {
  withLoading(
    buildingBlockApi
      .releaseBuildingBlock(props.buildingBlock.id)
      .then(() => {
        emit('release');

        notifications.success();
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingRelease
  );
}
</script>
