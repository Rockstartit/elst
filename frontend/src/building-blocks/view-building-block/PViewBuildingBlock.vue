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
        <q-tab-panel name="requirements" class="bg-grey-1">
          <TRequirements :building-block-version="buildingBlock.version" />
        </q-tab-panel>
      </q-tab-panels>
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { computed, onMounted, ref } from 'vue';
import {
  BuildingBlock,
  BuildingBlockVersion,
} from 'src/services/generated/openapi/building_blocks';
import { withLoading } from 'src/core/useWithLoading';
import { buildingBlockApi } from 'src/services';
import { useQuasar } from 'quasar';
import OBuildingBlockHeader, {
  BuildingBlockHeaderTab,
} from 'src/building-blocks/view-building-block/OBuildingBlockHeader.vue';
import TBuildingBlockOverview from 'src/building-blocks/view-building-block/TBuildingBlockOverview.vue';
import TRequirements from 'src/building-blocks/view-building-block/requirements/TRequirements.vue';

const quasar = useQuasar();

const props = defineProps<{
  buildingBlockId: string;
  version: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const buildingBlockVersion = computed<BuildingBlockVersion>(() => {
  return {
    buildingBlockId: props.buildingBlockId,
    version: Number.parseInt(props.version),
  };
});

const tab = ref<BuildingBlockHeaderTab>('overview');
const buildingBlock = ref<BuildingBlock>();

onMounted(() => {
  withLoading(
    buildingBlockApi
      .getBuildingBlock(
        buildingBlockVersion.value.buildingBlockId,
        buildingBlockVersion.value.version
      )
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
          .editBuildingBlock(
            buildingBlockVersion.value.buildingBlockId,
            buildingBlockVersion.value.version,
            {
              name: payload,
              description: buildingBlock.value.description,
            }
          )
          .then(() => {
            if (buildingBlock.value) {
              buildingBlock.value.name = payload;
            }
          });
      }
    });
}
</script>
