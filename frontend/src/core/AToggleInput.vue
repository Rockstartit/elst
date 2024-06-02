<template>
  <div>
    <slot v-if="showInput" name="input" :hideInput="hideInput" />

    <div v-else>
      <slot name="display" :displayInput="displayInput" />

      <slot name="toggle" :displayInput="displayInput" :hideInput="hideInput">
        <PrimaryButton
          label="Bearbeiten"
          icon="mdi-pencil-outline"
          text-color="primary"
          class="text-caption"
          dense
          flat
          @click="displayInput" />
      </slot>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';

const showInput = ref(false);

function displayInput() {
  showInput.value = true;
}

function hideInput() {
  showInput.value = false;
}
</script>
