<template>
  <div class="column">
    <div class="row">
      <div class="col">
        <p class="text-h6"> Diskussion </p>
      </div>

      <slot name="action" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { useDiscussions } from 'src/discussions/useDiscussions';

const {} = useDiscussions();
</script>
