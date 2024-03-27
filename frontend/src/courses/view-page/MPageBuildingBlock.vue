<template>
  <div class="bg-grey-1 elst__rounded elst__bordered q-pa-md">
    <q-item v-bind="$props" class="q-pa-none">
      <q-item-section avatar>
        <q-icon :name="icon" size="1.5rem" />
      </q-item-section>
      <q-item-section>
        <q-item-label>
          {{ buildingBlock.name }}
        </q-item-label>
        <q-item-label v-if="buildingBlock.description" caption>
          {{ buildingBlock.description }}
        </q-item-label>
      </q-item-section>
      <q-item-section side>
        <q-badge>{{ 'Version ' + buildingBlock.version }}</q-badge>
      </q-item-section>
      <slot name="after" />
    </q-item>
    <div v-if="inDevelopment" class="q-mt-sm">
      <p>
        Der Baustein befindet sich aktuell in der Entwicklung. Bevor der
        Baustein konfiguriert werden kann, muss er veröffentlicht werden.
      </p>
      <div
        class="row items-center text-body2 text-primary text-weight-medium cursor-pointer elst__link">
        <q-icon name="mdi-open-in-new" size="1rem" class="q-mr-sm" />
        Zur Entwicklung beitragen
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { QItemProps } from 'quasar';
import { PageBuildingBlock } from 'src/services/generated/openapi/courses';
import { computed } from 'vue';

const props = defineProps<
  {
    buildingBlock: PageBuildingBlock;
  } & QItemProps
>();

const inDevelopment = computed(
  () => props.buildingBlock.releaseStatus === 'IN_DEVELOPMENT'
);
const icon = computed(() =>
  inDevelopment.value ? 'mdi-wrench-outline' : 'mdi-open-in-new'
);
</script>
