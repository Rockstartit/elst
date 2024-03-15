<template>
  <q-item>
    <q-item-section side>
      <q-icon :name="icon" />
    </q-item-section>
    <q-item-section>
      <q-item-label>
        {{ value ?? 'Keine Angabe' }}
      </q-item-label>
    </q-item-section>
  </q-item>
</template>
<script lang="ts" setup>
defineProps<{
  icon: string;
  label: string;
  value?: string;
}>();
</script>
