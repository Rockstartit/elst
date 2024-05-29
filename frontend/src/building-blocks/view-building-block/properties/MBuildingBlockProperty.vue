<template>
  <div class="row q-hoverable elst__rounded relative-position q-px-md q-py-sm">
    <span class="q-focus-helper"></span>
    <div class="col">
      <div class="row items-baseline q-mb-sm">
        <kbd class="q-mr-sm">
          {{ property.key }}: {{ property.type.toLowerCase() }}
        </kbd>

        <span class="text-body1 text-grey-7">
          {{ property.displayName }}
        </span>
      </div>

      <p class="q-mb-none text-body1">
        {{
          property.description.length > 0
            ? property.description
            : 'Keine Beschreibung'
        }}
      </p>
    </div>

    <div class="col-auto column justify-center">
      <div class="row items-baseline" style="gap: 0.75rem">
        <slot name="actions" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { QItemProps } from 'quasar';
import { BuildingBlockProperty } from 'src/services/generated/openapi';

withDefaults(
  defineProps<
    {
      property: BuildingBlockProperty;
    } & QItemProps
  >(),
  {}
);
</script>
