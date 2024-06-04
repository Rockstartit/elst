<template>
  <div class="q-pa-md">
    <q-tree
      :selected="selectedPageId"
      :nodes="teachingUnitTree"
      node-key="value"
      selected-color="primary"
      default-expand-all
      no-selection-unset
      no-nodes-label="Im Unterricht wurde kein Unterrichtsverlaufsplan erstellt. Erstelle mindestens eine Unterrichtsphase, um den Kurs weiterplanen zu können."
      @update:selected="$emit('select-page', $event)">
      <template #header-teaching-unit="{ node }">
        <q-item dense class="full-width">
          <q-item-section>
            <q-item-label>
              {{ node.label }}
            </q-item-label>
          </q-item-section>
          <q-item-section side>
            <TertiaryButton
              icon="mdi-open-in-new"
              tooltip="Unterrichtseinheit anzeigen"
              dense
              flat
              :to="viewTeachingUnitRoute(lessonId, node.value)"
              @click.stop />
          </q-item-section>
        </q-item>
      </template>
      <template #header-phase="{ node }">
        <div class="col">
          <div class="row">
            <q-badge
              v-if="node.phase"
              :color="learningCyclePhaseColor(node.phase)"
              rounded
              class="justify-center text-grey-10 q-pa-xs text-weight-medium full-width"
              style="width: 150px">
              {{ learningCyclePhaseLabel(node.phase) }}
            </q-badge>
          </div>

          <q-item-label caption class="text-body2 q-mt-sm">
            Lehrerpräsenz:
            <span class="text-weight-medium">
              {{ getTeacherPresenceLabel(node.teacherPresence) }}
            </span>
          </q-item-label>

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
          class="preserve-line-breaks text-grey-10 q-mt-md"
          style="max-width: 90ch">
          {{ node.label }}
        </q-item-label>

        <q-chip
          v-if="node.learningMaterials.length > 0"
          rounded
          outline
          color="indigo"
          size="12px"
          class="cursor-pointer q-mt-md">
          <div class="row items-center no-wrap" style="gap: 0.3rem">
            <q-icon name="mdi-file-outline" />

            <span class="text-weight-medium">
              {{
                node.learningMaterials.length > 0
                  ? node.learningMaterials.length
                  : 'Keine'
              }}
            </span>

            <span> Lernmaterialien verfügbar </span>

            <q-icon
              v-if="node.learningMaterials.length > 0"
              name="mdi-chevron-down"
              size="1rem"
              class="" />
          </div>

          <q-menu>
            <q-list>
              <MLearningMaterial
                v-for="learningMaterial in node.learningMaterials"
                :key="learningMaterial.id"
                :learning-material="learningMaterial">
                <template #after>
                  <q-item-section side>
                    <PrimaryButton
                      icon="mdi-download-outline"
                      text-color="primary"
                      dense
                      flat
                      @click="downloadFile(learningMaterial.fileId)" />
                  </q-item-section>
                </template>
              </MLearningMaterial>
            </q-list>
          </q-menu>
        </q-chip>

        <div class="q-mt-md">
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
                :page="element"
                :active="selectedPageId === element.id"
                @edit-status="editPageImplementationStatus(element, $event)"
                @click="$emit('select-page', element.id)">
                <template #actions>
                  <div class="row">
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
import {
  CourseTeachingUnit,
  ImplementationStatus,
  Page,
} from 'src/services/generated/openapi';
import {
  confirmDialog,
  isRequired,
  stringPromptDialog,
} from 'src/core/useBaseDialog';
import { withLoadingArray } from 'src/core/useWithLoading';
import { pageApi } from 'src/services/course_conceptualization';
import { useNotifications } from 'src/core/useNotifications';
import { useQuasar } from 'quasar';
import MLearningMaterial from 'src/lessons/view-teaching-unit/MLearningMaterial.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useContentDownload } from 'src/core/useContentDownload';
import { useAppRouter } from 'src/router/useAppRouter';
import { useTeacherPresence } from 'src/lessons/view-teaching-unit/useTeacherPresence';

const quasar = useQuasar();
const notifications = useNotifications();
const { downloadFile } = useContentDownload();
const { viewTeachingUnitRoute } = useAppRouter();
const { getTeacherPresenceLabel } = useTeacherPresence();

const props = defineProps<{
  courseId: string;
  lessonId: string;
  selectedPageId?: string;
}>();

const emit = defineEmits<{
  (e: 'select-page', pageId: string | undefined): void;
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
      header: 'teaching-unit',
      children: teachingUnit.teachingPhases.map((teachingPhase) => {
        return {
          label: teachingPhase.topic,
          value: teachingPhase.id,
          phase: teachingPhase.phase,
          timeFrame: teachingPhase.timeFrame,
          pages: teachingPhase.pages,
          teacherPresence: teachingPhase.teacherPresence,
          learningMaterials: teachingPhase.learningMaterials,
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
            const pageId = response.data;

            notifications.created();

            const teachingPhase = courseTeachingUnits.value
              .flatMap((teachingUnit) => teachingUnit.teachingPhases)
              .find((teachingPhase) => teachingPhase.id === teachingPhaseId);

            if (teachingPhase) {
              const maxOrder = Math.max(
                ...teachingPhase.pages.map((page) => page.order)
              );

              teachingPhase.pages.push({
                id: pageId,
                teachingPhaseId: teachingPhaseId,
                title,
                linkedPages: [],
                buildingBlocks: [],
                mockups: [],
                order: maxOrder + 1,
                implementationStatus: ImplementationStatus.NotStarted,
              });

              emit('select-page', pageId);
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

            emit('select-page', undefined);
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

function editPageImplementationStatus(
  page: Page,
  status: ImplementationStatus
) {
  pageApi
    .editPage(page.id, {
      implementationStatus: status,
    })
    .then(() => {
      page.implementationStatus = status;

      notifications.saved();
    })
    .catch((err) => {
      notifications.apiError(err);
    });
}
</script>
