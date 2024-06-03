<template>
  <MHoverable
    no-background
    no-padding
    v-slot="{ hovered }"
    class="full-width text-black">
    <q-item
      clickable
      class="elst__rounded"
      :class="{
        'text-indigo-10 bg-indigo-1 text-weight-medium': active,
      }"
      @click="$emit('click', $event)">
      <q-item-section side>
        <q-icon
          name="mdi-drag"
          class="drag-handle"
          draggable="true"
          :color="active ? 'indigo-10' : undefined" />
      </q-item-section>

      <q-item-section side>
        <q-icon
          :name="getImplementationStatusIcon(page.implementationStatus)"
          :color="getImplementationStatusColor(page.implementationStatus)"
          @click.stop>
          <q-tooltip
            class="text-caption"
            anchor="top middle"
            self="bottom middle">
            Status der Umsetzung:
            <span class="text-weight-medium">
              {{ getImplementationStatusLabel(page.implementationStatus) }}
            </span>
          </q-tooltip>

          <q-menu>
            <q-list v-close-popup>
              <q-item
                v-for="status in implementationStatusList"
                :key="status"
                :active="status === page.implementationStatus"
                clickable
                @click="editImplementationStatus(status)">
                <q-item-section side>
                  <q-icon
                    :name="getImplementationStatusIcon(status)"
                    :color="getImplementationStatusColor(status)"
                    size="1.2rem" />
                </q-item-section>
                <q-item-section>
                  <q-item-label>
                    {{ getImplementationStatusLabel(status) }}
                  </q-item-label>
                </q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </q-icon>
      </q-item-section>

      <q-item-section>
        {{ page.title }}
      </q-item-section>

      <q-item-section v-if="hovered" side>
        <slot name="actions" />
      </q-item-section>
    </q-item>
  </MHoverable>
</template>

<script lang="ts" setup>
import MHoverable from 'src/core/MHoverable.vue';
import {
  ImplementationStatus,
  PageOverview,
} from 'src/services/generated/openapi';
import { useImplementationStatus } from 'src/courses/view-course/useImplementationStatus';

const {
  implementationStatusList,
  getImplementationStatusIcon,
  getImplementationStatusColor,
  getImplementationStatusLabel,
} = useImplementationStatus();

defineProps<{
  page: PageOverview;
  active?: boolean;
}>();

const emit = defineEmits<{
  (e: 'edit-status', status: ImplementationStatus): void;
  (e: 'click', event: never): void;
}>();

function editImplementationStatus(status: ImplementationStatus) {
  emit('edit-status', status);
}
</script>
