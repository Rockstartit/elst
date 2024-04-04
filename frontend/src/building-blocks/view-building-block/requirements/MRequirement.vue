<template>
  <div class="elst__rounded bg-grey-2 elst__bordered q-pb-xs">
    <div class="q-px-md q-pt-sm">
      <q-badge>
        <span class="text-capitalize">
          # {{ requirement.type.toLowerCase() }}
        </span>
      </q-badge>
    </div>
    <q-item dense>
      <q-item-section>
        <q-item-label>
          {{ requirement.content }}
        </q-item-label>
      </q-item-section>

      <slot name="after" />
    </q-item>
  </div>
</template>

<script lang="ts" setup>
import { BuildingBlockRequirement } from 'src/services/generated/openapi/building_blocks';

defineProps<{
  requirement: BuildingBlockRequirement;
}>();
</script>
