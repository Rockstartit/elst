<template>
  <div class="overflow-hidden q-mt-lg">
    <div class="row q-col-gutter-lg">
      <div class="col">
        <OMockups height="300px" />

        <OMarkdownRenderer :content="readme" class="q-mt-lg relative-position">
          <template #before>
            <SecondaryButton
              label="Bearbeiten"
              icon="mdi-pencil-outline"
              dense
              flat
              class="absolute text-caption"
              style="right: 12px; top: 12px"
              @click="openReadMeEditor" />
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
import OMockups from 'src/building-blocks/view-building-block/OMockups.vue';
import OMarkdownRenderer from 'src/core/OMarkdownRenderer.vue';
import OBuildingBlockDetails from 'src/building-blocks/view-building-block/OBuildingBlockDetails.vue';
import { BuildingBlock } from 'src/services/generated/openapi/building_blocks';
import { buildingBlockApi, fileApi } from 'src/services';
import { useQuasar } from 'quasar';
import { onMounted, ref } from 'vue';
import SecondaryButton from 'src/core/SecondaryButton.vue';
import MarkdownEditorDialog, {
  MarkdownEditorDialogProps,
} from 'src/core/MarkdownEditorDialog.vue';

const quasar = useQuasar();

const buildingBlock = defineModel<BuildingBlock>({ required: true });

const readme = ref('');

onMounted(() => {
  fileApi
    .getBuildingBlockReadMe(
      buildingBlock.value.version.buildingBlockId,
      buildingBlock.value.version.version
    )
    .then((response) => {
      readme.value = response.data;
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

function openReadMeEditor() {
  const dialogProps: MarkdownEditorDialogProps = {
    initialContent: readme.value,
  };

  quasar
    .dialog({
      component: MarkdownEditorDialog,
      componentProps: dialogProps,
    })
    .onOk((content) => {
      fileApi
        .editBuildingBlockReadMe(
          buildingBlock.value.version.buildingBlockId,
          buildingBlock.value.version.version,
          {
            content,
          }
        )
        .then(() => {
          readme.value = content;
        });
    });
}
</script>
