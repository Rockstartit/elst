<template>
  <PBase content-width="1200px">
    <h1 class="text-h5"> Evaluation einer Masterarbeit </h1>
    <p class="text-body1">
      Vielen Dank, dass Sie mich bei der Evaluation meiner Masterarbeit
      unterstützen.
    </p>

    <PrimaryButton
      label="Anmelden"
      class="elst__base-button-width"
      @click="login" />
  </PBase>
</template>

<script lang="ts" setup>
import PBase from 'src/core/PBase.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useAuthenticationStore } from 'stores/authentication/store';

const { login } = useAuthenticationStore();
</script>
