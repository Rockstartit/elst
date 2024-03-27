<template>
  <router-link v-bind="$props" style="text-decoration: none">
    <div class="row items-center cursor-pointer elst__link">
      <q-icon :name="icon" size="1rem" class="q-mr-sm" />
      {{ label }}
    </div>
  </router-link>
</template>

<script lang="ts" setup>
import { RouterLinkProps } from 'vue-router';

withDefaults(
  defineProps<
    {
      label: string;
      icon?: string;
    } & RouterLinkProps
  >(),
  {
    icon: 'mdi-open-in-new',
  }
);
</script>
