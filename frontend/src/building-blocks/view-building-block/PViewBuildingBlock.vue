<template>
  <PBase content-width="1200px">
    <div v-if="initialized && buildingBlock">
      <OBuildingBlockHeader
        v-model="tab"
        :name="buildingBlock.name"
        :technology="buildingBlock.technology"
        @edit-name="openEditNameDialog"
        @edit-technology="openEditTechnologyDialog" />

      <q-tab-panels v-model="tab">
        <q-tab-panel name="overview" class="q-px-none">
          <TBuildingBlockOverview v-model="buildingBlock" />
        </q-tab-panel>

        <q-tab-panel name="properties" class="q-px-none">
          <TBuildingBlockProperties v-model="buildingBlock" />
        </q-tab-panel>
      </q-tab-panels>
    </div>

    <template #breadcrumbs>
      <TheBreadcrumbs>
        <q-breadcrumbs-el class="text-black"> Bausteine </q-breadcrumbs-el>

        <q-breadcrumbs-el>
          {{ buildingBlock?.name }}
        </q-breadcrumbs-el>
      </TheBreadcrumbs>
    </template>
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
import TheBreadcrumbs from 'src/core/TheBreadcrumbs.vue';
import TBuildingBlockProperties from 'src/building-blocks/view-building-block/properties/TBuildingBlockProperties.vue';

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

function openEditTechnologyDialog() {
  quasar
    .dialog({
      title: 'Technologie bearbeiten',
      prompt: {
        model: buildingBlock.value?.technology ?? '',
      },
      cancel: true,
    })
    .onOk((payload) => {
      if (buildingBlock.value) {
        buildingBlockApi
          .editBuildingBlock(props.buildingBlockId, {
            name: buildingBlock.value.name,
            technology: payload,
            description: buildingBlock.value.description,
          })
          .then(() => {
            if (buildingBlock.value) {
              buildingBlock.value.technology = payload;
            }
          });
      }
    });
}
</script>
