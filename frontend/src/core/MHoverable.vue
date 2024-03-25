<template>
  <div class="hoverable q-px-sm q-py-xs cursor-pointer elst__rounded">
    <slot />
  </div>
</template>

<script lang="ts" setup></script>

<style lang="scss" scoped>
.hoverable:hover {
  background: $grey-3;
  padding: 4px 8px;
}
</style>
