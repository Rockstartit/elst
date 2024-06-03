<template>
  <q-dialog
    ref="dialogRef"
    class="text-grey-10"
    no-refocus
    transition-show="fadeIn"
    transition-hide="fadeOut"
    @hide="onDialogHide">
    <q-card style="width: 800px" class="q-dialog-plugin">
      <q-card-section>
        <h1 class="text-h6 q-my-none"> Baustein konfigurieren </h1>
      </q-card-section>
      <q-card-section v-if="loading">
        <div class="row justify-center">
          <q-spinner size="1.5rem" />
        </div>
      </q-card-section>

      <div v-else-if="initialized">
        <q-card-section class="column">
          <div v-for="(property, index) in properties" :key="property.key">
            <OPageBuildingBlockProperty
              v-model="properties[index]"
              :page-building-block-id="pageBuildingBlockId" />

            <q-separator v-if="index < properties.length - 1" class="q-my-md" />
          </div>
        </q-card-section>
      </div>
    </q-card>
  </q-dialog>
</template>

<script lang="ts" setup>
import { useDialogPluginComponent } from 'quasar';
import { ConfigurePageBuildingBlockDialogProps } from 'src/courses/view-course/useConfigurePageBuildingBlockDialog';
import { onMounted, ref } from 'vue';
import { PageBuildingBlockProperty } from 'src/services/generated/openapi';
import { withLoading } from 'src/core/useWithLoading';
import { pageBuildingBlockApi } from 'src/services/course_conceptualization';
import { useNotifications } from 'src/core/useNotifications';
import OPageBuildingBlockProperty from 'src/courses/view-course/OPageBuildingBlockProperty.vue';

const notifications = useNotifications();
const { dialogRef, onDialogHide } = useDialogPluginComponent();

const props = defineProps<ConfigurePageBuildingBlockDialogProps>();

defineEmits([...useDialogPluginComponent.emits]);

const initialized = ref(false);
const loading = ref(false);
const properties = ref<PageBuildingBlockProperty[]>([]);

onMounted(() => {
  withLoading(
    pageBuildingBlockApi
      .getBuildingBlockProperties(props.pageBuildingBlockId)
      .then((response) => {
        properties.value = response.data;
      })
      .catch((err) => {
        notifications.apiError(err);
      })
      .finally(() => {
        initialized.value = true;
      }),
    loading
  );
});
</script>
