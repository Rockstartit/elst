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
              <MPageBuildingBlock :building-block="pageBuildingBlock" />
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
import { CourseVersion, Page } from 'src/services/generated/openapi/courses';
import { withLoading } from 'src/core/useWithLoading';
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
</script>
