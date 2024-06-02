<template>
  <div class="q-pa-md">
    <q-tree
      :selected="selectedPageId"
      :nodes="teachingUnitTree"
      node-key="value"
      selected-color="primary"
      default-expand-all
      no-selection-unset
      @update:selected="$emit('select-page', $event)">
      <template #header-phase="{ node }">
        <div class="col q-mt-md">
          <div class="row">
            <q-badge
              v-if="node.phase"
              :color="learningCyclePhaseColor(node.phase)"
              rounded
              class="justify-center text-grey-10 q-pa-xs text-weight-medium full-width"
              style="width: 150px; max-width: 400px">
              {{ learningCyclePhaseLabel(node.phase) }}
            </q-badge>
          </div>

          <q-item-label caption class="text-body2 q-mt-sm">
            Geplante Dauer:
            <span class="text-weight-medium">
              {{ node.timeFrame ? node.timeFrame + ' Minuten' : 'Keine' }}
            </span>
          </q-item-label>
        </div>
      </template>
      <template #body-phase="{ node }">
        <q-item-label
          class="preserve-line-breaks text-grey-10 q-my-md"
          style="max-width: 90ch">
          {{ node.label }}
        </q-item-label>

        <div style="max-width: 500px">
          <Sortable
            :list="node.pages"
            item-key="id"
            tag="div"
            class="column"
            style="gap: 0.2rem"
            :options="sortableOptions"
            @update="onPagesReorder(node.value, $event)">
            <template #item="{ element }">
              <MPageItem
                :title="element.title"
                :active="selectedPageId === element.id"
                @click="$emit('select-page', element.id)">
                <template #actions>
                  <div class="row">
                    <TertiaryButton
                      icon="mdi-chat-outline"
                      text-color="grey-10"
                      dense
                      flat
                      tooltip="Diskussionen"
                      @click="togglePageDiscussionDrawer(element.id)" />

                    <TertiaryButton
                      icon="mdi-delete-outline"
                      text-color="grey-10"
                      dense
                      flat
                      tooltip="Löschen"
                      :loading="performingDeletePage.includes(element.id)"
                      @click="
                        openDeletePageDialog(
                          element.teachingPhaseId,
                          element.id
                        )
                      " />
                  </div>
                </template>
              </MPageItem>
            </template>
          </Sortable>
        </div>

        <SecondaryButton
          label="Neue Seite"
          icon="mdi-plus"
          color="indigo-1"
          text-color="indigo-10"
          dense
          flat
          class="text-caption elst__rounded q-mt-sm"
          style="max-width: 200px; min-width: 110px"
          :loading="performingCreatePage.includes(node.value)"
          @click.stop="openCreatePageDialog(node.value)" />
      </template>
    </q-tree>
  </div>
</template>

<script lang="ts" setup>
import {
  learningCyclePhaseColor,
  learningCyclePhaseLabel,
} from 'src/lessons/view-teaching-unit/useTeachingPhase';
import { sortableOptions } from 'src/core/useSortableList';
import { Sortable } from 'sortablejs-vue3';
import MPageItem from 'src/courses/view-course/MPageItem.vue';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import SecondaryButton from 'src/core/SecondaryButton.vue';
import { computed, ref } from 'vue';
import { CourseTeachingUnit } from 'src/services/generated/openapi';
import { useDiscussionDrawer } from 'src/discussions/useDiscussionDrawer';
import {
  confirmDialog,
  isRequired,
  stringPromptDialog,
} from 'src/core/useBaseDialog';
import { withLoadingArray } from 'src/core/useWithLoading';
import { pageApi } from 'src/services/course_conceptualization';
import { useNotifications } from 'src/core/useNotifications';
import { useQuasar } from 'quasar';

const quasar = useQuasar();
const notifications = useNotifications();
const { togglePageDiscussionDrawer } = useDiscussionDrawer();

const props = defineProps<{
  courseId: string;
  selectedPageId?: string;
}>();

defineEmits<{
  (e: 'select-page', pageId: string): void;
}>();

const courseTeachingUnits = defineModel<CourseTeachingUnit[]>({
  required: true,
});

const performingCreatePage = ref<string[]>([]);
const performingDeletePage = ref<string[]>([]);

const teachingUnitTree = computed(() =>
  courseTeachingUnits.value.map((teachingUnit) => {
    return {
      label: teachingUnit.topic,
      value: teachingUnit.id,
      selectable: false,
      children: teachingUnit.teachingPhases.map((teachingPhase) => {
        return {
          label: teachingPhase.topic,
          value: teachingPhase.id,
          phase: teachingPhase.phase,
          timeFrame: teachingPhase.timeFrame,
          pages: teachingPhase.pages,
          selectable: false,
          body: 'phase',
          header: 'phase',
        };
      }),
    };
  })
);

function openCreatePageDialog(teachingPhaseId: string) {
  quasar
    .dialog(
      stringPromptDialog('Neue Seite', 'Titel', {
        ok: {
          label: 'Erstellen',
        },
        isValid: isRequired,
      })
    )
    .onOk((title) => {
      withLoadingArray(
        pageApi
          .createPage(props.courseId, {
            teachingPhaseId,
            title,
          })
          .then((response) => {
            notifications.created();

            const teachingPhase = courseTeachingUnits.value
              .flatMap((teachingUnit) => teachingUnit.teachingPhases)
              .find((teachingPhase) => teachingPhase.id === teachingPhaseId);

            if (teachingPhase) {
              const maxOrder = Math.max(
                ...teachingPhase.pages.map((page) => page.order)
              );

              teachingPhase.pages.push({
                id: response.data,
                teachingPhaseId: teachingPhaseId,
                title,
                linkedPages: [],
                buildingBlocks: [],
                mockups: [],
                order: maxOrder + 1,
              });
            }
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingCreatePage,
        teachingPhaseId
      );
    });
}

function openDeletePageDialog(teachingPhaseId: string, pageId: string) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      pageApi
        .deletePage(pageId)
        .then(() => {
          notifications.deleted();

          const teachingPhase = courseTeachingUnits.value
            .flatMap((teachingUnit) => teachingUnit.teachingPhases)
            .find((teachingPhase) => teachingPhase.id === teachingPhaseId);

          if (!teachingPhase) {
            return;
          }

          const index = teachingPhase.pages.findIndex(
            (page) => page.id === pageId
          );
          if (index >= 0) {
            teachingPhase.pages.splice(index, 1);
          }
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingDeletePage,
      pageId
    );
  });
}

function onPagesReorder(
  teachingPhaseId: string,
  event: { oldIndex: number; newIndex: number }
) {
  const teachingPhase = courseTeachingUnits.value
    .flatMap((teachingUnit) => teachingUnit.teachingPhases)
    .find((teachingPhase) => teachingPhase.id === teachingPhaseId);

  if (!teachingPhase) {
    return;
  }

  const length = teachingPhase.pages.length ?? 0;

  if (event.oldIndex < length && event.newIndex < length) {
    const element = teachingPhase.pages.splice(event.oldIndex, 1)[0];
    teachingPhase.pages.splice(event.newIndex, 0, element);

    pageApi
      .reorderPages(
        props.courseId,
        teachingPhase.pages.map((page) => page.id)
      )
      .then(() => {
        notifications.success();

        teachingPhase.pages.forEach((page, index) => (page.order = index));
      })
      .catch((err) => {
        notifications.apiError(err);

        teachingPhase.pages.sort((a, b) => a.order - b.order);
      });
  }
}
</script>
