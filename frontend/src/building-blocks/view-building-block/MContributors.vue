<template>
  <div>
    <span class="text-body2 text-weight-medium"> {{ label }} </span>
    <div class="relative-position q-mt-sm" style="height: 80px">
      <q-avatar
        v-for="n in 5"
        :key="n"
        size="40px"
        class="overlapping"
        :style="`left: ${(n - 1) * 25}px`">
        <img :src="`https://cdn.quasar.dev/img/avatar${n + 1}.jpg`" />
      </q-avatar>
    </div>
  </div>
</template>

<script lang="ts" setup>
defineProps<{
  label: string;
}>();
</script>

<style lang="scss" scoped>
.overlapping {
  border: 2px solid white;
  position: absolute;
}
</style>
