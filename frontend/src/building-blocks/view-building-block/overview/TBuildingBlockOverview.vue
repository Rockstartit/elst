<template>
  <div>
    <div class="row q-col-gutter-lg">
      <div class="col">
        <div class="elst__bordered elst__rounded overflow-hidden">
          <div class="row bg-grey-2">
            <div class="col">
              <q-btn-toggle
                v-model="readmeMode"
                toggle-color="primary"
                flat
                no-caps
                :options="[
                  { label: 'Vorschau', value: 'view' },
                  {
                    label: 'Bearbeiten',
                    value: 'edit',
                  },
                ]" />
            </div>

            <div v-if="readmeMode === 'edit'" class="col-auto">
              <PrimaryButton
                label="Speichern"
                flat
                text-color="primary"
                icon="mdi-content-save-outline"
                @mousedown="saveReadMe" />
            </div>
          </div>

          <OMarkdownRenderer
            v-if="readmeMode === 'view'"
            :content="readme"
            class="q-pa-md" />

          <BaseInput
            v-if="readmeMode === 'edit'"
            v-model="readme"
            type="textarea"
            :outlined="false"
            input-class="q-pa-md"
            input-style="height: 600px" />
        </div>
      </div>

      <div class="col-auto">
        <OBuildingBlockDetails
          :building-block="buildingBlock"
          class="elst__detail-sidebar"
          @edit="openEditDescriptionDialog"
          @release="onBuildingBlockReleased" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import OMarkdownRenderer from 'src/core/OMarkdownRenderer.vue';
import OBuildingBlockDetails from 'src/building-blocks/view-building-block/overview/OBuildingBlockDetails.vue';
import { useQuasar } from 'quasar';
import { onMounted, ref } from 'vue';
import { BuildingBlock } from 'src/services/generated/openapi';
import {
  buildingBlockApi,
  buildingBlockReadMeApi,
} from 'src/services/building_blocks';
import { useNotifications } from 'src/core/useNotifications';
import BaseInput from 'src/core/BaseInput.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { withLoading } from 'src/core/useWithLoading';

const quasar = useQuasar();
const notifications = useNotifications();

const buildingBlock = defineModel<BuildingBlock>({ required: true });

const readme = ref('');
const readmeMode = ref('view');
const performingSaveReadMe = ref(false);

onMounted(() => {
  buildingBlockReadMeApi
    .getBuildingBlockReadMe(buildingBlock.value.id)
    .then((response) => {
      readme.value = response.data;
    })
    .catch((err) => {
      notifications.apiError(err);
    });
});

function openEditDescriptionDialog() {
  quasar
    .dialog({
      title: 'Beschreibung bearbeiten',
      prompt: {
        model: buildingBlock.value?.description ?? '',
      },
      cancel: true,
    })
    .onOk((payload) => {
      buildingBlockApi
        .editBuildingBlock(buildingBlock.value.id, {
          name: buildingBlock.value?.name,
          description: payload,
          technology: buildingBlock.value.technology,
        })
        .then(() => {
          buildingBlock.value.description = payload;

          notifications.saved();
        })
        .catch((err) => {
          notifications.apiError(err);
        });
    });
}

function saveReadMe() {
  withLoading(
    buildingBlockReadMeApi
      .editBuildingBlockReadMe(buildingBlock.value.id, {
        content: readme.value,
      })
      .then(() => {
        readmeMode.value = 'view';

        notifications.saved();
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingSaveReadMe
  );
}

function onBuildingBlockReleased() {
  buildingBlock.value.releaseStatus = 'RELEASED';
}
</script>
