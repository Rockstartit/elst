<template>
  <div class="overflow-hidden q-mt-lg">
    <div class="row q-col-gutter-lg">
      <div class="col">
        <OMockups height="300px" />

        <OMarkdownRenderer :content="exampleMarkdown" />
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
import OMockups from 'src/building-blocks/view-building-block/OMockups.vue';
import OMarkdownRenderer from 'src/core/OMarkdownRenderer.vue';
import OBuildingBlockDetails from 'src/building-blocks/view-building-block/OBuildingBlockDetails.vue';
import { BuildingBlock } from 'src/services/generated/openapi/building_blocks';
import { buildingBlockApi } from 'src/services';
import { useQuasar } from 'quasar';

const quasar = useQuasar();

const buildingBlock = defineModel<BuildingBlock>({ required: true });

const exampleMarkdown =
  '# ReadMe \nEine einfache Textkomponente, die das Anzeigen von nicht formatierten Texten ermöglicht.';

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
