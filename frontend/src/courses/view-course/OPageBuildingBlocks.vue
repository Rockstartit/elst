<template>
  <div>
    <div class="row items-baseline justify-between">
      <p class="text-body2 text-weight-medium"> Ausgewählte Bausteine </p>

      <PrimaryButton
        label="Baustein hinzufügen"
        icon="mdi-plus"
        class="text-caption"
        @click="openSelectBuildingBlockDialog(pageId)" />
    </div>
    <div class="row q-mt-md">
      <div class="col">
        <OPageBuildingBlockList
          :building-blocks="pageBuildingBlocks"
          initialized>
          <template #item="{ pageBuildingBlock }">
            <MPageBuildingBlock :building-block="pageBuildingBlock">
              <template #actions>
                <PrimaryButton
                  icon="mdi-delete"
                  dense
                  flat
                  text-color="grey-7"
                  hover-text-color="red-10"
                  hover-color="red-1"
                  tooltip="Baustein entfernen"
                  @click="openRemoveBuildingBlockDialog(pageBuildingBlock)" />

                <PrimaryButton
                  icon="mdi-cog-outline"
                  dense
                  flat
                  text-color="black"
                  :tooltip="
                    configurePageBuildingBlockTooltip(pageBuildingBlock)
                  "
                  :disable="
                    pageBuildingBlock.releaseStatus === 'IN_DEVELOPMENT'
                  "
                  @click="
                    openConfigureBuildingBlockDialog(
                      pageBuildingBlock.pageBuildingBlockId
                    )
                  " />

                <PrimaryButton
                  icon="mdi-open-in-new"
                  dense
                  flat
                  text-color="primary"
                  tooltip="Detailseite anzeigen"
                  :to="
                    viewBuildingBlockRoute(pageBuildingBlock.buildingBlockId)
                  " />
              </template>
            </MPageBuildingBlock>
          </template>
        </OPageBuildingBlockList>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import OPageBuildingBlockList from 'src/courses/view-course/OPageBuildingBlockList.vue';
import MPageBuildingBlock from 'src/courses/view-course/MPageBuildingBlock.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { PageBuildingBlock } from 'src/services/generated/openapi';
import { confirmDialog } from 'src/core/useBaseDialog';
import { withLoading, withLoadingArray } from 'src/core/useWithLoading';
import { pageBuildingBlockApi } from 'src/services/course_conceptualization';
import {
  selectBuildingBlockDialog,
  SelectBuildingBlockDialogResult,
} from 'src/courses/select-building-block/useSelectBuildingBlockDialog';
import { ref } from 'vue';
import { useQuasar } from 'quasar';
import { useNotifications } from 'src/core/useNotifications';
import { useAppRouter } from 'src/router/useAppRouter';
import { configurePageBuildingBlockDialog } from 'src/courses/view-course/useConfigurePageBuildingBlockDialog';

const quasar = useQuasar();
const notifications = useNotifications();
const { viewBuildingBlockRoute } = useAppRouter();

const props = defineProps<{
  pageId: string;
  technologyWish: string;
}>();

const pageBuildingBlocks = defineModel<PageBuildingBlock[]>({ required: true });

const performingRemovePageBuildingBlock = ref<string[]>([]);
const performingAddBuildingBlockToPage = ref(false);

function configurePageBuildingBlockTooltip(
  pageBuildingBlock: PageBuildingBlock
) {
  if (pageBuildingBlock.releaseStatus === 'IN_DEVELOPMENT') {
    return 'Der Baustein muss veröffentlicht sein, bevor dieser konfiguriert werden kann.';
  }

  return 'Baustein konfigurieren';
}

function openRemoveBuildingBlockDialog(pageBuildingBlock: PageBuildingBlock) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      pageBuildingBlockApi
        .removeBuildingBlockFromPage(pageBuildingBlock.pageBuildingBlockId)
        .then(() => {
          const index = pageBuildingBlocks.value.indexOf(pageBuildingBlock);

          if (index >= 0) {
            pageBuildingBlocks.value.splice(index, 1);
          }

          notifications.deleted();
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingRemovePageBuildingBlock,
      pageBuildingBlock.pageBuildingBlockId
    );
  });
}

function openSelectBuildingBlockDialog(pageId: string) {
  if (!props.technologyWish) {
    console.error('could not find technologyWish');
    return;
  }

  quasar
    .dialog(selectBuildingBlockDialog(props.technologyWish))
    .onOk((result: SelectBuildingBlockDialogResult) => {
      withLoading(
        pageBuildingBlockApi
          .addBuildingBlockToPage(pageId, {
            buildingBlockId: result.buildingBlock.id,
          })
          .then((response) => {
            pageBuildingBlocks.value.push({
              pageBuildingBlockId: response.data,
              buildingBlockId: result.buildingBlock.id,
              name: result.buildingBlock.name,
              releaseStatus: result.buildingBlock.releaseStatus,
              description: result.buildingBlock.description,
            });

            notifications.success();
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingAddBuildingBlockToPage
      );
    });
}

function openConfigureBuildingBlockDialog(pageBuildingBlockId: string) {
  quasar.dialog(configurePageBuildingBlockDialog(pageBuildingBlockId));
}
</script>
