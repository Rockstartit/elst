<template>
  <q-item v-bind="$props" class="bg-grey-1 elst__rounded elst__bordered">
    <q-item-section side>
      <q-icon name="mdi-file-outline" />
    </q-item-section>
    <q-item-section>
      <q-item-label>
        {{
          mockup.description && mockup.description.length > 0
            ? mockup.description
            : 'Designvorschlag'
        }}
      </q-item-label>
    </q-item-section>

    <slot name="after" />
  </q-item>
</template>

<script setup lang="ts">
import { Mockup } from 'src/services/generated/openapi';
import { QItemProps } from 'quasar';

withDefaults(
  defineProps<
    {
      mockup: Mockup;
    } & QItemProps
  >(),
  {}
);
</script>
