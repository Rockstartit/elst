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
import OBuildingBlockList from 'src/building_blocks/browse/OBuildingBlockList.vue';
import MBuildingBlockOverview from 'src/building_blocks/browse/MBuildingBlockOverview.vue';
import { computed, ref } from 'vue';
import { CourseVersion } from 'src/services/generated/openapi/courses';
import { BuildingBlock } from 'src/services/generated/openapi/building_blocks';
import { withLoading } from 'src/core/useWithLoading';
import { buildingBlockApi, pageApi } from 'src/services';
import { useAppRouter } from 'src/router/useAppRouter';
import MRequestNewBuildingBlock from 'src/courses/select-building-block/MRequestNewBuildingBlock.vue';
import { useQuasar } from 'quasar';

const quasar = useQuasar();
const { viewPage } = useAppRouter();

const props = defineProps<{
  courseId: string;
  version: number;
  courseUnitId: string;
  pageId: string;
}>();

const performingSelect = ref(false);

const courseVersion = computed<CourseVersion>(() => {
  return {
    courseId: props.courseId,
    version: props.version,
  };
});

function selectBuildingBlock(buildingBlock: BuildingBlock) {
  return withLoading(
    pageApi
      .addBuildingBlockToPage(props.pageId, {
        buildingBlockId: buildingBlock.id,
        version: buildingBlock.version,
      })
      .then(() => {
        viewPage(courseVersion.value, props.courseUnitId, props.pageId, true);
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
                viewPage(
                  courseVersion.value,
                  props.courseUnitId,
                  props.pageId,
                  true
                );
              }),
            performingSelect
          );
        });
    });
}
</script>
