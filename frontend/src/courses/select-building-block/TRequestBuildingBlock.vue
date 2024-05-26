<template>
  <q-form @submit="requestBuildingBlock">
    <div class="column">
      <q-item class="bg-indigo-1 elst__rounded elst__bordered text-indigo-5">
        <q-item-section side>
          <q-icon name="mdi-information-outline" color="indigo-5" />
        </q-item-section>

        <q-item-section>
          <q-item-label>
            Alle Informationen können im Nachhinein bearbeitet werden und dienen
            primär als Ausgangslage für vertiefende Gespräche mit einem
            Entwickler.
          </q-item-label>
        </q-item-section>
      </q-item>

      <div class="column q-mt-lg" style="gap: 0.75rem">
        <BaseInput v-model="name" label="Name" :rules="[isRequired]" />

        <BaseInput v-model="description" label="Beschreibung" type="textarea" />

        <div class="row justify-end q-mt-sm">
          <PrimaryButton
            label="Beantragen"
            class="elst__base-button-width"
            type="submit" />
        </div>
      </div>
    </div>
  </q-form>
</template>

<script lang="ts" setup>
import BaseInput from 'src/core/BaseInput.vue';
import { ref } from 'vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useRules } from 'src/core/useRules';
import { withLoading } from 'src/core/useWithLoading';
import { buildingBlockApi } from 'src/services/building_blocks';
import { BuildingBlock, ReleaseStatus } from 'src/services/generated/openapi';

const { isRequired } = useRules();

const props = defineProps<{
  technologyWish: string;
}>();

const emit = defineEmits<{
  (e: 'request', buildingBlock: BuildingBlock): void;
}>();

const performingRequestBuildingBlock = ref(false);

const name = ref('');
const description = ref('');

function requestBuildingBlock() {
  return withLoading(
    buildingBlockApi
      .requestBuildingBlock({
        name: name.value,
        description: description.value,
        technology: props.technologyWish,
      })
      .then((response) => {
        emit('request', {
          id: response.data,
          name: name.value,
          description: description.value,
          technology: props.technologyWish,
          releaseStatus: ReleaseStatus.InDevelopment,
          properties: [],
        });
      }),
    performingRequestBuildingBlock
  );
}
</script>
