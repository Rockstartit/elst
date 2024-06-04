<template>
  <q-menu @before-show="initialize">
    <div v-if="loading" class="row justify-center q-px-md q-py-sm">
      <q-spinner />
    </div>
    <q-list v-else v-close-popup>
      <p
        v-if="filteredPages.length === 0"
        class="text-grey-7 q-px-md q-py-sm q-mb-none">
        Keine Seiten verfügbar
      </p>
      <q-item
        v-for="page in filteredPages"
        :key="page.id"
        clickable
        @click="$emit('select', page)">
        <q-item-section>
          <q-item-label>
            {{ page.title }}
          </q-item-label>
        </q-item-section>
      </q-item>
    </q-list>
  </q-menu>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import { pageApi } from 'src/services/course_conceptualization';
import { PageOverview } from 'src/services/generated/openapi';
import { withLoading } from 'src/core/useWithLoading';

const props = defineProps<{
  courseId: string;
  excludePageIds?: string[];
}>();

defineEmits<{
  (e: 'select', page: PageOverview): void;
}>();

const pages = ref<PageOverview[]>([]);
const loading = ref(false);

const filteredPages = computed(() =>
  pages.value.filter((page) => !(props.excludePageIds ?? []).includes(page.id))
);

function initialize() {
  withLoading(
    pageApi.getPages(props.courseId).then((response) => {
      pages.value = response.data;
    }),
    loading
  );
}
</script>
