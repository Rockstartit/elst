<template>
  <div class="overflow-hidden">
    <div class="row q-col-gutter-lg">
      <div class="col">
        <OMarkdownRenderer :content="readme" class="q-mt-lg relative-position">
          <template #before>
            <SecondaryButton
              label="Bearbeiten"
              icon="mdi-pencil-outline"
              dense
              flat
              class="absolute text-caption"
              style="right: 12px; top: 12px" />
          </template>
        </OMarkdownRenderer>
      </div>

      <div class="col-auto">
        <OBuildingBlockDetails
          :building-block="buildingBlock"
          class="elst__detail-sidebar"
          @edit="openEditDescriptionDialog" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import OMarkdownRenderer from 'src/core/OMarkdownRenderer.vue';
import OBuildingBlockDetails from 'src/building-blocks/view-building-block/overview/OBuildingBlockDetails.vue';
import { useQuasar } from 'quasar';
import { ref } from 'vue';
import SecondaryButton from 'src/core/SecondaryButton.vue';
import { BuildingBlock } from 'src/services/generated/openapi';
import { buildingBlockApi } from 'src/services/building_blocks';

const quasar = useQuasar();

const buildingBlock = defineModel<BuildingBlock>({ required: true });

const readme = ref('');

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
        .editBuildingBlock(
          buildingBlock.value.version.buildingBlockId,
          buildingBlock.value.version.version,
          {
            name: buildingBlock.value?.name,
            description: payload,
          }
        )
        .then(() => {
          buildingBlock.value.description = payload;
        });
    });
}
</script>
