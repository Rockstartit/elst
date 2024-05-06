<template>
  <div
    class="  "
    :class="{
      hoverable: !noBackground,
      'q-px-sm q-py-xs': !noPadding,
      'cursor-pointer': clickable,
      elst__rounded: rounded,
    }"
    @mouseenter="hovered = true"
    @mouseleave="hovered = false">
    <slot :hovered="hovered" />
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';

withDefaults(
  defineProps<{
    noPadding?: boolean;
    noBackground?: boolean;
    clickable?: boolean;
    rounded?: boolean;
  }>(),
  {
    rounded: true,
    clickable: true,
  }
);

const hovered = ref(false);
</script>

<style lang="scss" scoped>
.hoverable:hover {
  background: $grey-3;
  padding: 4px 8px;
}
</style>
