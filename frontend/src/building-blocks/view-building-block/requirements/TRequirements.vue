<template>
  <div class="overflow-hidden">
    <div class="row q-mb-md">
      <PrimaryButton
        label="Neue Anforderung"
        icon="mdi-plus"
        @click="openDocumentRequirementDialog" />
    </div>

    <q-list class="column" style="gap: 1rem">
      <MRequirement
        v-for="requirement in sortedRequirements"
        :key="requirement.id"
        :requirement="requirement">
        <template #after>
          <q-item-section side top>
            <div class="row" style="gap: 0.75rem">
              <TertiaryButton
                icon="mdi-pencil-outline"
                dense
                flat
                text-color="grey-10"
                @click="openEditRequirementDialog(requirement)" />

              <TertiaryButton
                icon="mdi-delete-outline"
                dense
                flat
                text-color="grey-10"
                @click="deleteRequirement(requirement)" />
            </div>
          </q-item-section>
        </template>
      </MRequirement>
    </q-list>
  </div>
</template>

<script lang="ts" setup>
import { computed, onMounted, ref } from 'vue';
import { withLoading } from 'src/core/useWithLoading';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useQuasar } from 'quasar';
import CreateOrEditRequirementDialog, {
  CreateOrEditRequirementDialogProps,
  CreateOrEditRequirementDialogRequest,
} from 'src/building-blocks/view-building-block/requirements/CreateOrEditRequirementDialog.vue';
import MRequirement from 'src/building-blocks/view-building-block/requirements/MRequirement.vue';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import {
  BuildingBlockRequirement,
  BuildingBlockVersion,
} from 'src/services/generated/openapi';
import { requirementApi } from 'src/services/building_blocks';

const quasar = useQuasar();

const props = defineProps<{
  buildingBlockVersion: BuildingBlockVersion;
}>();

const initialized = ref(false);
const loading = ref(false);

const requirements = ref<BuildingBlockRequirement[]>([]);
const sortedRequirements = computed(() =>
  [...requirements.value].sort((a, b) => a.type.localeCompare(b.type))
);

onMounted(() => {
  withLoading(
    requirementApi
      .getAllRequirements(
        props.buildingBlockVersion.buildingBlockId,
        props.buildingBlockVersion.version
      )
      .then((response) => {
        requirements.value = response.data;
      })
      .finally(() => {
        initialized.value = true;
      }),
    loading
  );
});

function openDocumentRequirementDialog() {
  quasar
    .dialog({
      component: CreateOrEditRequirementDialog,
    })
    .onOk((request: CreateOrEditRequirementDialogRequest) => {
      requirementApi
        .documentRequirement(
          props.buildingBlockVersion.buildingBlockId,
          props.buildingBlockVersion.version,
          {
            type: request.type,
            content: request.content,
          }
        )
        .then((response) => {
          requirements.value.push({
            id: response.data,
            content: request.content,
            type: request.type,
          });
        });
    });
}

function openEditRequirementDialog(requirement: BuildingBlockRequirement) {
  const dialogProps: CreateOrEditRequirementDialogProps = {
    initialContent: requirement.content,
    initialType: requirement.type,
  };

  quasar
    .dialog({
      component: CreateOrEditRequirementDialog,
      componentProps: dialogProps,
    })
    .onOk((request: CreateOrEditRequirementDialogRequest) => {
      requirementApi
        .editRequirement(requirement.id, {
          type: request.type,
          content: request.content,
        })
        .then(() => {
          requirement.type = request.type;
          requirement.content = request.content;
        });
    });
}

function deleteRequirement(requirement: BuildingBlockRequirement) {
  requirementApi.deleteRequirement(requirement.id).then(() => {
    const index = requirements.value.indexOf(requirement);

    if (index >= 0) {
      requirements.value.splice(index, 1);
    }
  });
}
</script>
