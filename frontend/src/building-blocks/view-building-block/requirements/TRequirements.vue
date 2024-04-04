<template>
  <div class="overflow-hidden">
    <div class="row q-mb-md">
      <PrimaryButton label="Neue Anforderung" icon="mdi-plus" />
    </div>

    {{ sortedRequirements }}
  </div>
</template>

<script lang="ts" setup>
import { computed, onMounted, ref } from 'vue';
import {
  BuildingBlockRequirement,
  BuildingBlockVersion,
} from 'src/services/generated/openapi/building_blocks';
import { withLoading } from 'src/core/useWithLoading';
import { requirementApi } from 'src/services';
import PrimaryButton from 'src/core/PrimaryButton.vue';

const props = defineProps<{
  buildingBlockVersion: BuildingBlockVersion;
}>();

const initialized = ref(false);
const loading = ref(false);

const requirements = ref<BuildingBlockRequirement[]>([]);
const sortedRequirements = computed(() =>
  [...requirements.value].sort((a, b) => a.type.localeCompare(b.type))
);

onMounted(() => {
  withLoading(
    requirementApi
      .getAllRequirements(
        props.buildingBlockVersion.buildingBlockId,
        props.buildingBlockVersion.version
      )
      .then((response) => {
        requirements.value = response.data;
      })
      .finally(() => {
        initialized.value = true;
      }),
    loading
  );
});
</script>
