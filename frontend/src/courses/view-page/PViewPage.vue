<template>
  <PBase content-width="1200px">
    <div v-if="initialized && page">
      <OPageHeader :title="page.title" @edit-name="openEditTitleDialog" />

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
                        performingRemoveBuildingBlock.includes(
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
              :to="selectBuildingBlockRoute(courseId, pageId)" />
          </div>
        </div>
      </div>
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { onMounted, ref } from 'vue';
import { withLoading, withLoadingArray } from 'src/core/useWithLoading';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import OPageHeader from 'src/courses/view-page/OPageHeader.vue';
import OPageBuildingBlockList from 'src/courses/view-page/OPageBuildingBlockList.vue';
import MPageBuildingBlock from 'src/courses/view-page/MPageBuildingBlock.vue';
import { useQuasar } from 'quasar';
import { Page, PageBuildingBlock } from 'src/services/generated/openapi';
import { pageApi } from 'src/services/course_conceptualization';
import { isRequired, stringPromptDialog } from 'src/core/useBaseDialog';
import { useNotifications } from 'src/core/useNotifications';
import { useAppRouter } from 'src/router/useAppRouter';

const quasar = useQuasar();
const notifications = useNotifications();
const { selectBuildingBlockRoute } = useAppRouter();

const props = defineProps<{
  courseId: string;
  pageId: string;
}>();

const initialized = ref(false);
const loading = ref(false);
const page = ref<Page>();

const performingRemoveBuildingBlock = ref<string[]>([]);

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
        notifications.success();

        if (page.value) {
          const index = page.value.buildingBlocks.indexOf(buildingBlock);

          page.value.buildingBlocks.splice(index, 1);
        }
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingRemoveBuildingBlock,
    buildingBlock.pageBuildingBlockId
  );
}

function openEditTitleDialog() {
  quasar
    .dialog(
      stringPromptDialog('Titel bearbeiten', 'Titel', {
        ok: {
          label: 'Speichern',
        },
        isValid: isRequired,
        model: page.value?.title,
      })
    )
    .onOk((payload) => {
      if (page.value) {
        pageApi
          .editPage(props.pageId, {
            title: payload,
          })
          .then(() => {
            notifications.saved();

            if (page.value) {
              page.value.title = payload;
            }
          })
          .catch((err) => {
            notifications.apiError(err);
          });
      }
    });
}
</script>
