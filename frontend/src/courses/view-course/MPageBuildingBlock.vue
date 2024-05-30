<template>
  <div class="bg-grey-1 elst__rounded elst__bordered q-pa-md">
    <q-item v-bind="$props" class="q-pa-none">
      <q-item-section avatar>
        <q-icon
          :name="
            inDevelopment ? 'mdi-wrench-outline' : 'mdi-package-variant-closed'
          "
          size="1.5rem" />
      </q-item-section>
      <q-item-section>
        <q-item-label class="text-h6 row items-baseline">
          {{ buildingBlock.name }}

          <q-icon
            v-if="buildingBlock.description"
            name="mdi-information-outline"
            class="text-body1 q-ml-sm">
            <q-tooltip class="text-caption">
              {{ buildingBlock.description }}
            </q-tooltip>
          </q-icon>
        </q-item-label>
      </q-item-section>
      <slot name="after" />
    </q-item>
    <div v-if="inDevelopment" class="q-mt-sm">
      <p>
        Der Baustein befindet sich aktuell in der Entwicklung. Bevor der
        Baustein konfiguriert werden kann, muss er veröffentlicht werden.
      </p>

      <ALink
        label="Zur Entwicklung beitragen"
        :to="viewBuildingBlockRoute(buildingBlock.buildingBlockId)"
        class="text-body2 text-primary text-weight-medium" />
    </div>

    <q-list v-if="!inDevelopment">
      <q-item-label header class="text-black text-weight-medium text-body2">
        Konfigurationsmöglichkeiten
      </q-item-label>
      <MPageBuildingBlockProperty
        v-for="property in buildingBlock.properties"
        :key="property.key"
        :property="property" />
    </q-list>
  </div>
</template>

<script lang="ts" setup>
import { QItemProps } from 'quasar';
import { computed } from 'vue';
import { useAppRouter } from 'src/router/useAppRouter';
import ALink from 'src/core/ALink.vue';
import { PageBuildingBlock } from 'src/services/generated/openapi';
import MPageBuildingBlockProperty from 'src/courses/view-course/MPageBuildingBlockProperty.vue';

const { viewBuildingBlockRoute } = useAppRouter();

const props = defineProps<
  {
    buildingBlock: PageBuildingBlock;
  } & QItemProps
>();

const inDevelopment = computed(
  () => props.buildingBlock.releaseStatus === 'IN_DEVELOPMENT'
);
</script>
