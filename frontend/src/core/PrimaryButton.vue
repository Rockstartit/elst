<template>
  <q-btn
    v-bind="$props"
    :text-color="actualTextColor"
    :color="actualColor"
    unelevated
    @mouseover="hovered = true"
    @mouseleave="hovered = false">
    <slot />

    <BaseTooltip v-if="tooltip">
      {{ tooltip }}
    </BaseTooltip>
  </q-btn>
</template>

<script lang="ts" setup>
import { computed, ref } from 'vue';
import { QBtnProps } from 'quasar';
import BaseTooltip from 'src/core/BaseTooltip.vue';

const props = withDefaults(
  defineProps<
    {
      hoverTextColor?: string;
      hoverColor?: string;
      tooltip?: string;
      noCaps?: boolean;
    } & QBtnProps
  >(),
  {
    color: 'primary',
    textColor: 'indigo-1',
    noCaps: true,
  }
);

const hovered = ref(false);

const actualTextColor = computed(() => {
  if (hovered.value && props.hoverTextColor) {
    return props.hoverTextColor;
  }

  return props.textColor;
});

const actualColor = computed(() => {
  if (hovered.value && props.hoverColor) {
    return props.hoverColor;
  }

  return props.color;
});
</script>
