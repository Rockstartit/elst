<template>
  <q-breadcrumbs>
    <template #separator>
      <q-icon name="mdi-chevron-right" size="1rem" style="color: inherit" />
    </template>

    <q-breadcrumbs-el
      class="cursor-pointer"
      :to="{ name: availableRoutes.app_base }">
      <q-icon name="mdi-home-outline" size="1rem" class="q-mr-sm" />

      Startseite
    </q-breadcrumbs-el>

    <slot />
  </q-breadcrumbs>
</template>

<script setup lang="ts">
import { availableRoutes } from 'src/router/routes';
</script>
