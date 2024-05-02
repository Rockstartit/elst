<template>
  <transition enter-active-class="animated fadeIn">
    <div v-if="fetching" class="row justify-center q-py-md">
      <q-spinner />
    </div>

    <q-list
      v-else-if="initialized && !fetching"
      class="column"
      style="gap: 0.75rem">
      <slot name="before" />

      <div v-if="items.length > 0" class="column" :style="'gap: ' + gap">
        <slot
          name="item"
          v-for="(item, index) in items"
          :key="keyFn(item)"
          :item="item"
          :index="index" />
      </div>

      <div v-else-if="initialized" class="row justify-center q-mt-sm">
        {{ emptyMessage }}
      </div>

      <slot name="after" />
    </q-list>
  </transition>
</template>

<script lang="ts" setup generic="T">
import { uid } from 'quasar';

withDefaults(
  defineProps<{
    items: T[];
    keyFn?: (item: T) => string;
    initialized?: boolean;
    fetching?: boolean;
    emptyMessage?: string;
    gap?: string;
  }>(),
  {
    initialized: true,
    fetching: false,
    emptyMessage: 'Keine Elemente verfügbar',
    gap: '0.75rem',
    keyFn: (item: T) => {
      if (item && typeof item === 'object' && 'id' in item) {
        return item.id as string;
      }

      return uid();
    },
  }
);
</script>
