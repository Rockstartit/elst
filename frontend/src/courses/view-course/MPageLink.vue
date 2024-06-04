<template>
  <q-item class="elst__rounded">
    <q-item-section side>
      <q-icon name="mdi-link" />
    </q-item-section>
    <q-item-section>
      <q-item-label>
        {{ pageLink.targetPageTitle }}
      </q-item-label>
      <q-item-label caption>
        {{
          (pageLink.condition?.length ?? 0) > 0
            ? pageLink.condition
            : 'Keine Bedingung'
        }}
      </q-item-label>
    </q-item-section>
    <q-item-section side>
      <div class="row" style="gap: 0.75rem">
        <slot name="actions" />
      </div>
    </q-item-section>
  </q-item>
</template>

<script lang="ts" setup>
import { PageLink } from 'src/services/generated/openapi';

defineProps<{
  pageLink: PageLink;
  active?: boolean;
}>();
</script>
