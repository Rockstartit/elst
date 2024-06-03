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
        <Sortable
          :list="pageBuildingBlocks"
          item-key="pageBuildingBlockId"
          tag="div"
          class="column"
          style="gap: 0.75rem"
          :options="sortableOptions"
          @update="onPageBuildingBlocksReorder">
          <template #item="{ element }">
            <MPageBuildingBlock :building-block="element">
              <template #before>
                <q-item-section side>
                  <q-icon
                    name="mdi-drag"
                    class="drag-handle"
                    draggable="true" />
                </q-item-section>
              </template>
              <template #actions>
                <PrimaryButton
                  icon="mdi-delete"
                  dense
                  flat
                  text-color="grey-7"
                  hover-text-color="red-10"
                  hover-color="red-1"
                  tooltip="Baustein entfernen"
                  @click="openRemoveBuildingBlockDialog(element)" />

                <PrimaryButton
                  icon="mdi-cog-outline"
                  dense
                  flat
                  text-color="black"
                  :tooltip="configurePageBuildingBlockTooltip(element)"
                  :disable="element.releaseStatus === 'IN_DEVELOPMENT'"
                  @click="
                    openConfigureBuildingBlockDialog(
                      element.pageBuildingBlockId
                    )
                  " />

                <PrimaryButton
                  icon="mdi-open-in-new"
                  dense
                  flat
                  text-color="primary"
                  tooltip="Detailseite anzeigen"
                  :to="viewBuildingBlockRoute(element.buildingBlockId)" />
              </template>
            </MPageBuildingBlock>
          </template>
        </Sortable>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
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
import { onMounted, ref } from 'vue';
import { useQuasar } from 'quasar';
import { useNotifications } from 'src/core/useNotifications';
import { useAppRouter } from 'src/router/useAppRouter';
import { configurePageBuildingBlockDialog } from 'src/courses/view-course/useConfigurePageBuildingBlockDialog';
import { sortableOptions } from 'src/core/useSortableList';
import { Sortable } from 'sortablejs-vue3';

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

onMounted(() => {
  pageBuildingBlocks.value.sort((a, b) => a.order - b.order);
});

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
            const maxOrder = Math.max(
              ...pageBuildingBlocks.value.map(
                (pageBuildingBlock) => pageBuildingBlock.order
              )
            );

            pageBuildingBlocks.value.push({
              pageBuildingBlockId: response.data,
              buildingBlockId: result.buildingBlock.id,
              name: result.buildingBlock.name,
              releaseStatus: result.buildingBlock.releaseStatus,
              description: result.buildingBlock.description,
              order: maxOrder + 1,
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

function onPageBuildingBlocksReorder(event: {
  oldIndex: number;
  newIndex: number;
}) {
  const length = pageBuildingBlocks.value.length ?? 0;

  if (event.oldIndex < length && event.newIndex < length) {
    const element = pageBuildingBlocks.value.splice(event.oldIndex, 1)[0];
    pageBuildingBlocks.value.splice(event.newIndex, 0, element);

    pageBuildingBlockApi
      .reorderPageBuildingBlocks(
        props.pageId,
        pageBuildingBlocks.value.map(
          (pageBuildingBlock) => pageBuildingBlock.pageBuildingBlockId
        )
      )
      .then(() => {
        notifications.success();

        pageBuildingBlocks.value.forEach(
          (pageBuildingBlock, index) => (pageBuildingBlock.order = index)
        );
      })
      .catch((err) => {
        notifications.apiError(err);

        pageBuildingBlocks.value.sort((a, b) => a.order - b.order);
      });
  }
}
</script>
