<template>
  <div>
    <q-list class="column" style="gap: 0.75rem">
      <MBuildingBlockProperty
        v-for="property in sortedProperties"
        :key="property.key"
        :property="property">
        <template #actions>
          <TertiaryButton
            icon="mdi-pencil-outline"
            dense
            flat
            :loading="performingEditProperty.includes(property.key)"
            @click="openEditPropertyDialog(property)" />

          <TertiaryButton
            icon="mdi-delete-outline"
            dense
            flat
            hover-color="red-1"
            hover-text-color="red-10"
            :loading="performingDeleteProperty.includes(property.key)"
            @click="openDeletePropertyDialog(property)" />
        </template>
      </MBuildingBlockProperty>

      <p v-if="buildingBlock.properties.length === 0">
        Keine Konfigurationsmöglichkeiten
      </p>
    </q-list>

    <PrimaryButton
      label="Neue Konfigurationsmöglichkeit"
      class="q-mt-lg"
      :loading="performingCreateProperty"
      @click="openCreatePropertyDialog" />
  </div>
</template>

<script lang="ts" setup>
import {
  BuildingBlock,
  BuildingBlockProperty,
} from 'src/services/generated/openapi';
import MBuildingBlockProperty from 'src/building-blocks/view-building-block/properties/MBuildingBlockProperty.vue';
import { computed, ref } from 'vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useQuasar } from 'quasar';
import {
  CreateOrEditPropertyDialogResult,
  createPropertyDialog,
  editPropertyDialog,
} from 'src/building-blocks/view-building-block/properties/useCreateOrEditPropertyDialog';
import { buildingBlockPropertyApi } from 'src/services/building_blocks';
import { useNotifications } from 'src/core/useNotifications';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import { withLoading, withLoadingArray } from 'src/core/useWithLoading';
import { confirmDialog } from 'src/core/useBaseDialog';

const quasar = useQuasar();
const notifications = useNotifications();

const buildingBlock = defineModel<BuildingBlock>({ required: true });

const sortedProperties = computed(() =>
  [...buildingBlock.value.properties].sort((a, b) => a.order - b.order)
);

const performingCreateProperty = ref(false);
const performingEditProperty = ref<string[]>([]);
const performingDeleteProperty = ref<string[]>([]);

function openCreatePropertyDialog() {
  quasar
    .dialog(createPropertyDialog())
    .onOk((result: CreateOrEditPropertyDialogResult) => {
      withLoading(
        buildingBlockPropertyApi
          .createBuildingBlockProperty(buildingBlock.value.id, {
            key: result.key,
            displayName: result.displayName,
            description: result.description,
            type: 'STRING',
          })
          .then(() => {
            const maxOrder = Math.max(
              ...buildingBlock.value.properties.map(
                (property) => property.order
              )
            );

            buildingBlock.value.properties.push({
              key: result.key,
              description: result.description,
              displayName: result.displayName,
              order: maxOrder + 1,
              type: 'STRING',
            });

            notifications.created();
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingCreateProperty
      );
    });
}

function openEditPropertyDialog(property: BuildingBlockProperty) {
  quasar
    .dialog(editPropertyDialog(property))
    .onOk((result: CreateOrEditPropertyDialogResult) => {
      withLoadingArray(
        buildingBlockPropertyApi
          .editBuildingBlockProperty(buildingBlock.value.id, property.key, {
            displayName: result.displayName,
            description: result.description,
            type: 'STRING',
          })
          .then(() => {
            property.displayName = result.displayName;
            property.description = result.description;

            notifications.saved();
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingEditProperty,
        property.key
      );
    });
}

function openDeletePropertyDialog(property: BuildingBlockProperty) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      buildingBlockPropertyApi
        .deleteBuildingBlockProperty(buildingBlock.value.id, property.key)
        .then(() => {
          const index = buildingBlock.value.properties.indexOf(property);

          if (index >= 0) {
            buildingBlock.value.properties.splice(index, 1);
          }

          notifications.deleted();
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingDeleteProperty,
      property.key
    );
  });
}
</script>
