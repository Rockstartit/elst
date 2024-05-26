<template>
  <PBase content-width="1200px">
    <div v-if="initialized && buildingBlock">
      <OBuildingBlockHeader
        v-model="tab"
        :name="buildingBlock.name"
        @edit-name="openEditNameDialog" />

      <q-tab-panels v-model="tab">
        <q-tab-panel name="overview" class="bg-grey-1">
          <TBuildingBlockOverview v-model="buildingBlock" />
        </q-tab-panel>
      </q-tab-panels>
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { onMounted, ref } from 'vue';
import { withLoading } from 'src/core/useWithLoading';
import { useQuasar } from 'quasar';
import OBuildingBlockHeader, {
  BuildingBlockHeaderTab,
} from 'src/building-blocks/view-building-block/OBuildingBlockHeader.vue';
import TBuildingBlockOverview from 'src/building-blocks/view-building-block/overview/TBuildingBlockOverview.vue';
import { BuildingBlock } from 'src/services/generated/openapi';
import { buildingBlockApi } from 'src/services/building_blocks';

const quasar = useQuasar();

const props = defineProps<{
  buildingBlockId: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const tab = ref<BuildingBlockHeaderTab>('overview');
const buildingBlock = ref<BuildingBlock>();

onMounted(() => {
  withLoading(
    buildingBlockApi
      .getBuildingBlock(props.buildingBlockId)
      .then((response) => {
        buildingBlock.value = response.data;
      })
      .finally(() => {
        initialized.value = true;
      }),
    loading
  );
});

function openEditNameDialog() {
  quasar
    .dialog({
      title: 'Name bearbeiten',
      prompt: {
        model: buildingBlock.value?.name ?? '',
      },
      cancel: true,
    })
    .onOk((payload) => {
      if (buildingBlock.value) {
        buildingBlockApi
          .editBuildingBlock(props.buildingBlockId, {
            name: payload,
            technology: buildingBlock.value.technology,
            description: buildingBlock.value.description,
          })
          .then(() => {
            if (buildingBlock.value) {
              buildingBlock.value.name = payload;
            }
          });
      }
    });
}
</script>
