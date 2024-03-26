<template>
  <q-item
    v-bind="$props"
    class="bg-grey-1 elst__rounded elst__bordered q-py-sm q-px-md"
    @mouseenter="hover = true"
    @mouseleave="hover = false">
    <q-item-section avatar>
      <q-icon name="mdi-open-in-new" size="1.5rem" />
    </q-item-section>
    <q-item-section>
      <q-item-label>
        {{ courseUnit.description ?? 'Neue Kurseinheit' }}
      </q-item-label>
    </q-item-section>
    <slot name="after" :hover="hover" />
  </q-item>
</template>

<script lang="ts" setup>
import { QItemProps } from 'quasar';
import { CourseUnit } from 'src/services/generated/openapi/courses';
import { ref } from 'vue';

defineProps<
  {
    courseUnit: CourseUnit;
  } & QItemProps
>();

const hover = ref(false);
</script>
