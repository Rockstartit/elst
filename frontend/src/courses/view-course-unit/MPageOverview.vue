<template>
  <q-item
    v-bind="$props"
    class="bg-grey-1 elst__rounded elst__bordered q-pa-md">
    <q-item-section avatar>
      <q-icon name="mdi-open-in-new" size="1.5rem" />
    </q-item-section>
    <q-item-section>
      <q-item-label>
        {{ page.title }}
      </q-item-label>
    </q-item-section>
  </q-item>
</template>

<script lang="ts" setup>
import { QItemProps } from 'quasar';
import { PageOverview } from 'src/services/generated/openapi/courses';

defineProps<
  {
    page: PageOverview;
  } & QItemProps
>();
</script>
