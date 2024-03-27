<template>
  <PBase content-width="1200px">
    <div v-if="initialized && buildingBlock">
      <OCourseHeader
        :name="buildingBlock.name"
        @edit-name="openEditNameDialog" />

      <div class="overflow-hidden q-mt-lg">
        <div class="row q-col-gutter-lg">
          <div class="col">
            Anforderungen

            <div class="row justify-center q-mt-md">
              <PrimaryButton label="Neue Anforderung" />
            </div>
          </div>

          <div class="col-auto">
            <OBuildingBlockDetails
              :building-block="buildingBlock"
              class="elst__detail-sidebar"
              @edit="openEditNameDialog" />
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
  BuildingBlock,
  BuildingBlockVersion,
} from 'src/services/generated/openapi/building_blocks';
import { withLoading } from 'src/core/useWithLoading';
import { buildingBlockApi } from 'src/services';
import OCourseHeader from 'src/courses/view-course/OCourseHeader.vue';
import { useQuasar } from 'quasar';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import OBuildingBlockDetails from 'src/building-blocks/view-building-block/OBuildingBlockDetails.vue';

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
