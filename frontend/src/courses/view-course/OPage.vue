<template>
  <div class="overflow-hidden elst__rounded bg-grey-2">
    <q-item clickable :to="viewPageRoute(courseId, page.id)">
      <q-item-section avatar>
        <q-icon name="mdi-monitor" size="32px" color="grey-7" />
      </q-item-section>
      <q-item-section class="text-body1">
        <q-item-label class="text-weight-medium q-mb-xs">
          {{ page.title }}
        </q-item-label>

        <div class="row q-py-xs" style="gap: 0.5rem">
          <AIconChip
            icon="mdi-image-multiple-outline"
            :value="page.mockups.length.toString()"
            label="Mockups" />

          <AIconChip
            icon="mdi-package-variant-closed"
            :value="page.buildingBlocks.length.toString()"
            label="Bausteine" />
        </div>
      </q-item-section>

      <q-item-section side>
        <TertiaryButton
          icon="mdi-delete-outline"
          dense
          flat
          text-color="grey-10"
          hover-text-color="red-10"
          hover-color="red-1"
          :loading="loadingDelete"
          @click.stop="$emit('delete')" />
      </q-item-section>
    </q-item>
  </div>
</template>

<script setup lang="ts">
import { Page } from 'src/services/generated/openapi';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import AIconChip from 'src/courses/view-course/AIconChip.vue';
import { useAppRouter } from 'src/router/useAppRouter';

const { viewPageRoute } = useAppRouter();

defineProps<{
  courseId: string;
  loadingDelete?: boolean;
}>();

defineEmits(['delete']);

const page = defineModel<Page>({ required: true });
</script>
