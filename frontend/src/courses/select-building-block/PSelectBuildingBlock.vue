<template>
  <PBase content-width="600px">
    <h1 class="text-h6 text-weight-regular"> Baustein auswählen </h1>

    <OBuildingBlockList>
      <template #before>
        <MRequestNewBuildingBlock
          clickable
          @click="openRequestBuildingBlockDialog" />
      </template>
      <template #item="{ buildingBlock }">
        <MBuildingBlockOverview
          :building-block="buildingBlock"
          clickable
          @click="selectBuildingBlock(buildingBlock)" />
      </template>
    </OBuildingBlockList>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import OBuildingBlockList from 'src/building-blocks/browse/OBuildingBlockList.vue';
import MBuildingBlockOverview from 'src/building-blocks/browse/MBuildingBlockOverview.vue';
import { ref } from 'vue';
import { withLoading } from 'src/core/useWithLoading';
import { useAppRouter } from 'src/router/useAppRouter';
import MRequestNewBuildingBlock from 'src/courses/select-building-block/MRequestNewBuildingBlock.vue';
import { useQuasar } from 'quasar';
import { BuildingBlock } from 'src/services/generated/openapi';
import { pageApi } from 'src/services/course_conceptualization';
import { buildingBlockApi } from 'src/services/building_blocks';

const quasar = useQuasar();
const { viewPage } = useAppRouter();

const props = defineProps<{
  courseId: string;
  pageId: string;
}>();

const performingSelect = ref(false);

function selectBuildingBlock(buildingBlock: BuildingBlock) {
  return withLoading(
    pageApi
      .addBuildingBlockToPage(props.pageId, {
        buildingBlockId: buildingBlock.version.buildingBlockId,
        version: buildingBlock.version.version,
      })
      .then(() => {
        viewPage(props.courseId, props.pageId);
      }),
    performingSelect
  );
}

function openRequestBuildingBlockDialog() {
  quasar
    .dialog({
      title: 'Neuer Baustein',
      prompt: {
        title: 'Name',
        model: '',
      },
      cancel: true,
    })
    .onOk((name) => {
      buildingBlockApi
        .requestBuildingBlock({
          name,
        })
        .then((response) => {
          return withLoading(
            pageApi
              .addBuildingBlockToPage(props.pageId, {
                buildingBlockId: response.data.buildingBlockId,
                version: response.data.version,
              })
              .then(() => {
                viewPage(props.courseId, props.pageId);
              }),
            performingSelect
          );
        });
    });
}
</script>
