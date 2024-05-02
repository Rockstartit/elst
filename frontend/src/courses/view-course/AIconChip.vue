<template>
  <q-badge rounded color="indigo-7" outline class="shadow-0">
    <div class="row q-px-sm items-center text-caption" style="gap: 0.5rem">
      <q-icon :name="icon" size="14px" />
      <span class="text-weight-medium">
        {{ value }}
      </span>
      <span>{{ label }}</span>
    </div>
  </q-badge>
</template>
<script lang="ts" setup>
defineProps<{
  label: string;
  value: string;
  icon: string;
}>();
</script>
