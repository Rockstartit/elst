<template>
  <q-item
    v-bind="$props"
    class="bg-grey-1 elst__rounded elst__bordered q-pa-md">
    <q-item-section avatar>
      <q-icon name="mdi-open-in-new" size="1.5rem" />
    </q-item-section>
    <q-item-section>
      <q-item-label>
        {{ course.name }}
      </q-item-label>
    </q-item-section>
    <q-item-section side>
      <q-badge>{{ 'Code ' + course.code }}</q-badge>
    </q-item-section>
  </q-item>
</template>

<script lang="ts" setup>
import { QItemProps } from 'quasar';
import { Course } from 'src/services/generated/openapi/courses';

defineProps<
  {
    course: Course;
  } & QItemProps
>();
</script>
