<template>
  <OBaseAnimatedList
    :key-fn="keyFn"
    :items="pages"
    :initialized="initialized"
    :fetching="fetching"
    empty-message="Keine Seiten hinzugefügt">
    <template #item="{ item }">
      <slot name="item" :page="item" />
    </template>
  </OBaseAnimatedList>
</template>

<script lang="ts" setup>
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import { Page, PageOverview } from 'src/services/generated/openapi/courses';

defineProps<{
  pages: Page[];
  fetching?: boolean;
  initialized?: boolean;
}>();

function keyFn(page: PageOverview) {
  return page.id;
}
</script>
