<template>
  <div>
    <q-item v-bind="$props">
      <q-item-section>
        <q-item-label class="text-weight-medium">
          {{ property.displayName }}
        </q-item-label>
        <q-item-label v-if="property.description" caption>
          {{ property.description }}
        </q-item-label>
      </q-item-section>
    </q-item>

    <AToggleInput class="q-px-md q-mt-sm">
      <template #display>
        <p class="text-body2 preserve-line-breaks">
          {{ propertyValue.length > 0 ? propertyValue : 'Kein Wert' }}
        </p>
      </template>

      <template #input="{ hideInput }">
        <BaseInput v-model="propertyValue" autofocus autogrow />

        <div class="row justify-end q-mt-md" style="gap: 0.75rem">
          <PrimaryButton
            label="Abbrechen"
            class="elst__base-button-width"
            @click="resetPropertyValue(hideInput)" />

          <PrimaryButton
            label="Speichern"
            :loading="performingSave"
            class="elst__base-button-width"
            @click="savePropertyValue(hideInput)" />
        </div>
      </template>
    </AToggleInput>
  </div>
</template>

<script lang="ts" setup>
import { PageBuildingBlockProperty } from 'src/services/generated/openapi';
import { QItemProps } from 'quasar';
import { ref } from 'vue';
import BaseInput from 'src/core/BaseInput.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import AToggleInput from 'src/core/AToggleInput.vue';
import { pageBuildingBlockApi } from 'src/services/course_conceptualization';
import { withLoading } from 'src/core/useWithLoading';
import { useNotifications } from 'src/core/useNotifications';

const notifications = useNotifications();

const props = defineProps<
  {
    pageBuildingBlockId: string;
  } & QItemProps
>();

const property = defineModel<PageBuildingBlockProperty>({ required: true });

const propertyValue = ref(property.value.value);

const performingSave = ref(false);

function savePropertyValue(hideInput?: () => void) {
  return withLoading(
    pageBuildingBlockApi
      .editBuildingBlockProperty(
        props.pageBuildingBlockId,
        property.value.key,
        {
          value: propertyValue.value,
        }
      )
      .then(() => {
        notifications.saved();

        property.value.value = propertyValue.value;

        if (hideInput) {
          hideInput();
        }
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingSave
  );
}

function resetPropertyValue(hideInput?: () => void) {
  propertyValue.value = property.value.value;

  if (hideInput) {
    hideInput();
  }
}
</script>
