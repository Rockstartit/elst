<template>
  <q-item v-bind="$props" class="bg-grey-1 elst__rounded elst__bordered">
    <q-item-section avatar>
      <q-icon
        :name="
          inDevelopment ? 'mdi-wrench-outline' : 'mdi-package-variant-closed'
        "
        size="1.2rem" />
    </q-item-section>
    <q-item-section>
      <q-item-label class="text-body1 row items-baseline">
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
    <q-item-section side>
      <div class="row" style="gap: 0.5rem">
        <slot name="actions" />
      </div>
    </q-item-section>
  </q-item>
</template>

<script lang="ts" setup>
import { QItemProps } from 'quasar';
import { computed } from 'vue';
import { PageBuildingBlock } from 'src/services/generated/openapi';

const props = defineProps<
  {
    buildingBlock: PageBuildingBlock;
  } & QItemProps
>();

const inDevelopment = computed(
  () => props.buildingBlock.releaseStatus === 'IN_DEVELOPMENT'
);
</script>
