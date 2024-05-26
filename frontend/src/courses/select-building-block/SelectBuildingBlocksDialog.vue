<template>
  <q-dialog
    ref="dialogRef"
    class="text-grey-10"
    no-refocus
    full-height
    transition-show="fadeIn"
    transition-hide="fadeOut"
    @hide="onDialogHide">
    <q-card style="width: 800px" class="q-dialog-plugin">
      <q-tabs v-model="tab" active-bg-color="indigo-1" active-color="indigo-7">
        <q-tab :name="selectBuildingBlockTab" no-caps>
          <div class="full-width row justify-start"> Baustein auswählen </div>
        </q-tab>
        <q-tab :name="requestBuildingBlockTab" no-caps>
          Neuen Baustein beantragen
        </q-tab>
      </q-tabs>

      <q-card-section>
        <q-tab-panels v-model="tab" keep-alive>
          <q-tab-panel :name="selectBuildingBlockTab">
            <TSelectBuildingBlock @select="selectBuildingBlock" />
          </q-tab-panel>
          <q-tab-panel :name="requestBuildingBlockTab">
            <TRequestBuildingBlock
              :technology-wish="technologyWish"
              @request="selectBuildingBlock" />
          </q-tab-panel>
        </q-tab-panels>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script lang="ts" setup>
import { useDialogPluginComponent } from 'quasar';
import { BuildingBlock } from 'src/services/generated/openapi';
import {
  SelectBuildingBlockDialogProps,
  SelectBuildingBlockDialogResult,
} from 'src/courses/select-building-block/useSelectBuildingBlockDialog';
import { ref } from 'vue';
import TSelectBuildingBlock from 'src/courses/select-building-block/TSelectBuildingBlock.vue';
import TRequestBuildingBlock from 'src/courses/select-building-block/TRequestBuildingBlock.vue';

const { dialogRef, onDialogHide, onDialogOK } = useDialogPluginComponent();

defineProps<SelectBuildingBlockDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const selectBuildingBlockTab = 'select-building-block';
const requestBuildingBlockTab = 'request-building-block';

const tab = ref(selectBuildingBlockTab);

function selectBuildingBlock(buildingBlock: BuildingBlock) {
  const result: SelectBuildingBlockDialogResult = {
    buildingBlock,
  };

  onDialogOK(result);
}
</script>
