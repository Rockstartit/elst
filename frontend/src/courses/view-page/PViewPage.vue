<template>
  <PBase content-width="1200px">
    <div v-if="initialized && page">
      <OPageHeader :title="page.title" />

      <div class="row q-mt-lg">
        <div class="col">
          <OPageBuildingBlockList
            :building-blocks="page.buildingBlocks"
            :initialized="initialized"
            :fetching="loading">
            <template #item="{ pageBuildingBlock }">
              <MPageBuildingBlock :building-block="pageBuildingBlock">
                <template #after>
                  <q-item-section side>
                    <PrimaryButton
                      icon="mdi-delete"
                      dense
                      flat
                      text-color="grey-6"
                      hover-text-color="red-10"
                      hover-color="red-1"
                      :loading="
                        perfornimgRemoveBuildingBlock.includes(
                          pageBuildingBlock.pageBuildingBlockId
                        )
                      "
                      @click="removeBuildingBlock(pageBuildingBlock)" />
                  </q-item-section>
                </template>
              </MPageBuildingBlock>
            </template>
          </OPageBuildingBlockList>

          <div class="row justify-center q-mt-md">
            <PrimaryButton
              label="Baustein hinzufügen"
              :to="
                selectBuildingBlockRoute(courseVersion, courseUnitId, pageId)
              " />
          </div>
        </div>
      </div>
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { computed, onMounted, ref } from 'vue';
import {
  CourseVersion,
  Page,
  PageBuildingBlock,
} from 'src/services/generated/openapi/courses';
import { withLoading, withLoadingArray } from 'src/core/useWithLoading';
import { pageApi } from 'src/services';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import OPageHeader from 'src/courses/view-page/OPageHeader.vue';
import { useAppRouter } from 'src/router/useAppRouter';
import OPageBuildingBlockList from 'src/courses/view-page/OPageBuildingBlockList.vue';
import MPageBuildingBlock from 'src/courses/view-page/MPageBuildingBlock.vue';

const { selectBuildingBlockRoute } = useAppRouter();

const props = defineProps<{
  courseId: string;
  version: number;
  courseUnitId: string;
  pageId: string;
}>();

const courseVersion = computed<CourseVersion>(() => {
  return {
    courseId: props.courseId,
    version: props.version,
  };
});

const initialized = ref(false);
const loading = ref(false);
const page = ref<Page>();

const perfornimgRemoveBuildingBlock = ref<string[]>([]);

onMounted(() => {
  withLoading(
    pageApi
      .getPage(props.pageId)
      .then((response) => {
        page.value = response.data;
      })
      .finally(() => {
        initialized.value = true;
      }),
    loading
  );
});

function removeBuildingBlock(buildingBlock: PageBuildingBlock) {
  return withLoadingArray(
    pageApi
      .removeBuildingBlockFromPage(buildingBlock.pageBuildingBlockId)
      .then(() => {
        if (page.value) {
          const index = page.value.buildingBlocks.indexOf(buildingBlock);

          page.value.buildingBlocks.splice(index, 1);
        }
      }),
    perfornimgRemoveBuildingBlock,
    buildingBlock.pageBuildingBlockId
  );
}
</script>
