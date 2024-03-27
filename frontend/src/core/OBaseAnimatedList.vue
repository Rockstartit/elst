<template>
  <transition enter-active-class="animated fadeIn">
    <div v-if="initialized && fetching" class="row justify-center q-py-md">
      <q-spinner />
    </div>

    <q-list
      v-else-if="initialized && items.length > 0"
      class="column"
      style="gap: 0.75rem">
      <slot name="before" />
      <slot name="item" v-for="item in items" :key="keyFn(item)" :item="item" />
      <slot name="after" />
    </q-list>

    <div v-else-if="initialized" class="row justify-center">
      {{ emptyMessage }}
    </div>
  </transition>
</template>

<script lang="ts" setup generic="T">
withDefaults(
  defineProps<{
    items: T[];
    keyFn: (item: T) => string;
    initialized?: boolean;
    fetching?: boolean;
    emptyMessage?: string;
  }>(),
  {
    initialized: true,
    fetching: false,
    emptyMessage: 'Keine Elemente verfügbar',
  }
);
</script>
