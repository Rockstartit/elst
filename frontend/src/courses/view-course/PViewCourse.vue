<template>
  <PBase content-width="1600px">
    <div
      v-if="initialized && lesson && course"
      class="column"
      style="gap: 1.5rem">
      <OCourseHeader
        :lesson-id="lessonId"
        :topic="lesson.topic"
        :technology-wish="course.technologyWish"
        @edit-technology-wish="openEditTechnologyWishDialog" />

      <div>
        <BaseInput
          v-model="course.notes"
          type="textarea"
          autogrow
          placeholder="Hier können Hinweise zur Umsetzung des E-Learning Kurses dokumentiert werden."
          hint="Umsetzungshinweise für Entwickler"
          input-style="min-height: 300px" />

        <PrimaryButton
          v-if="canSaveNotes"
          label="Speichern"
          :loading="performingSaveNotes"
          class="q-mt-md elst__base-button-width"
          @click="saveNotes" />
      </div>

      <div>
        <p class="text-h6 text-weight-medium q-mb-sm"> Kursstruktur </p>
        <p class="text-body2 text-grey-7">
          Die Kursstruktur orientiert sich an den Unterrichtseinheiten aus der
          Unterrichtsplanung.
        </p>

        <div>
          <q-splitter v-model="splitterModel">
            <template v-slot:before>
              <div class="q-pa-md">
                <q-tree
                  :selected="selectedPageId"
                  :nodes="teachingUnitTree"
                  node-key="value"
                  selected-color="primary"
                  default-expand-all
                  no-selection-unset
                  @update:selected="selectTeachingPhase">
                  <template #header-page="{ node }">
                    <MHoverable
                      no-background
                      no-padding
                      v-slot="{ hovered }"
                      class="full-width">
                      <div class="row items-center" style="min-height: 32px">
                        <div class="col-auto q-pr-sm">
                          <q-icon v-if="node.icon" :name="node.icon" />
                        </div>

                        <div class="col">
                          {{ node.label }}
                        </div>

                        <div v-if="hovered" class="col-auto q-pl-sm">
                          <div class="row">
                            <TertiaryButton
                              icon="mdi-chat-outline"
                              text-color="grey-10"
                              dense
                              flat
                              tooltip="Diskussionen"
                              @click="
                                togglePageDiscussionDrawer(node.pageId)
                              " />

                            <TertiaryButton
                              v-if="hovered"
                              icon="mdi-delete-outline"
                              text-color="grey-10"
                              dense
                              flat
                              tooltip="Löschen"
                              :loading="
                                performingDeletePage.includes(node.pageId)
                              "
                              @click="
                                openDeletePageDialog(
                                  node.teachingPhaseId,
                                  node.pageId
                                )
                              " />
                          </div>
                        </div>
                      </div>
                    </MHoverable>
                  </template>

                  <template #header-add-page="{ node }">
                    <SecondaryButton
                      label="Neue Seite"
                      icon="mdi-plus"
                      color="indigo-1"
                      text-color="indigo-10"
                      dense
                      class="text-caption elst__rounded full-width"
                      style="max-width: 200px; min-width: 110px"
                      :loading="
                        performingCreatePage.includes(node.teachingPhaseId)
                      "
                      @click.stop="
                        openCreatePageDialog(node.teachingPhaseId)
                      " />
                  </template>

                  <template #body-phase="{ node }">
                    <q-badge
                      v-if="node.phase"
                      :color="learningCyclePhaseColor(node.phase)"
                      rounded
                      class="justify-center text-grey-10 q-pa-xs text-weight-medium"
                      style="width: 150px">
                      {{ learningCyclePhaseLabel(node.phase) }}
                    </q-badge>

                    <q-item-label caption class="text-body2 q-mt-sm">
                      Geplante Dauer:
                      <span class="text-weight-medium">
                        {{
                          node.timeFrame ? node.timeFrame + ' Minuten' : 'Keine'
                        }}
                      </span>
                    </q-item-label>
                  </template>
                </q-tree>
              </div>
            </template>

            <template v-slot:after>
              <div v-if="selectedPage" class="q-pa-md">
                <div class="col">
                  <div class="row q-mb-md">
                    <MHoverable
                      v-ripple
                      class="relative-position"
                      @click="openEditPageTitle(selectedPage)">
                      <div class="row text-h6 items-center" style="gap: 0.5rem">
                        <span>
                          {{ selectedPage.title }}
                        </span>
                        <q-icon name="mdi-pencil-outline" />
                      </div>
                    </MHoverable>
                  </div>

                  <div class="row q-col-gutter-lg">
                    <div class="col">
                      <OPageBuildingBlockList
                        :building-blocks="selectedPage.buildingBlocks"
                        :initialized="initialized"
                        :fetching="loading">
                        <template #item="{ pageBuildingBlock }">
                          <MPageBuildingBlock
                            :building-block="pageBuildingBlock">
                            <template #after>
                              <q-item-section side>
                                <PrimaryButton
                                  icon="mdi-delete"
                                  dense
                                  flat
                                  text-color="grey-6"
                                  hover-text-color="red-10"
                                  hover-color="red-1"
                                  @click="
                                    openRemoveBuildingBlockDialog(
                                      pageBuildingBlock
                                    )
                                  " />
                              </q-item-section>
                            </template>
                          </MPageBuildingBlock>
                        </template>
                      </OPageBuildingBlockList>

                      <div class="row justify-center q-mt-md">
                        <PrimaryButton
                          label="Baustein hinzufügen"
                          @click="
                            openSelectBuildingBlockDialog(selectedPage.id)
                          " />
                      </div>
                    </div>

                    <div class="col-auto" style="min-width: 350px">
                      <p class="text-body2 text-weight-medium"> Mockups </p>
                      <q-list class="column" style="gap: 0.5rem">
                        <MMockup
                          v-for="mockup in selectedPage.mockups"
                          :key="mockup.id"
                          :mockup="mockup">
                          <template #after>
                            <q-item-section side>
                              <div class="row" style="gap: 0.75rem">
                                <PrimaryButton
                                  icon="mdi-download-outline"
                                  text-color="primary"
                                  dense
                                  flat
                                  @click="downloadFile(mockup.fileId)" />

                                <TertiaryButton
                                  icon="mdi-delete-outline"
                                  dense
                                  flat
                                  hover-color="red-1"
                                  hover-text-color="red-10"
                                  :loading="
                                    performingDeleteMockup.includes(mockup.id)
                                  "
                                  @click="openDeleteMockupDialog(mockup)" />
                              </div>
                            </q-item-section>
                          </template>
                        </MMockup>

                        <div
                          class="row"
                          :class="{
                            'q-py-md': selectedPage.mockups.length > 0,
                          }">
                          <BaseUploader
                            multiple
                            :factory="mockupUploadFactory"
                            color="transparent"
                            text-color="grey-8"
                            class="bg-grey-2 full-width" />
                        </div>
                      </q-list>
                    </div>
                  </div>
                </div>
              </div>
            </template>
          </q-splitter>
        </div>
      </div>
    </div>

    <template #breadcrumbs>
      <TheBreadcrumbs>
        <q-breadcrumbs-el
          class="cursor-pointer"
          :to="{ name: availableRoutes.view_lesson, params: { lessonId } }">
          {{ lesson?.topic }}
        </q-breadcrumbs-el>
        <q-breadcrumbs-el class="text-black"> Kurse </q-breadcrumbs-el>
        <q-breadcrumbs-el>
          {{ course?.technologyWish }}
        </q-breadcrumbs-el>
      </TheBreadcrumbs>
    </template>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import { computed, onMounted, ref } from 'vue';
import { withLoading, withLoadingArray } from 'src/core/useWithLoading';
import {
  courseApi,
  mockupApi,
  pageApi,
} from 'src/services/course_conceptualization';
import {
  Course,
  CourseTeachingUnit,
  Lesson,
  Mockup,
  Page,
  PageBuildingBlock,
} from 'src/services/generated/openapi';
import { lessonApi } from 'src/services/lesson_planning';
import OCourseHeader from 'src/courses/view-course/OCourseHeader.vue';
import { QUploaderFactoryObject, useQuasar } from 'quasar';
import { useNotifications } from 'src/core/useNotifications';
import {
  confirmDialog,
  isRequired,
  stringPromptDialog,
} from 'src/core/useBaseDialog';
import BaseInput from 'src/core/BaseInput.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import MPageBuildingBlock from 'src/courses/view-course/MPageBuildingBlock.vue';
import OPageBuildingBlockList from 'src/courses/view-course/OPageBuildingBlockList.vue';
import SecondaryButton from 'src/core/SecondaryButton.vue';
import {
  learningCyclePhaseColor,
  learningCyclePhaseLabel,
} from 'src/lessons/view-teaching-unit/useTeachingPhase';
import MHoverable from 'src/core/MHoverable.vue';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import MMockup from 'src/courses/view-course/MMockup.vue';
import BaseUploader from 'src/core/BaseUploader.vue';
import { basePath } from 'boot/axios';
import { useAuthenticationStore } from 'stores/authentication/store';
import { useContentDownload } from 'src/core/useContentDownload';
import { useDiscussionDrawer } from 'src/discussions/useDiscussionDrawer';
import TheBreadcrumbs from 'src/core/TheBreadcrumbs.vue';
import { availableRoutes } from 'src/router/routes';
import {
  selectBuildingBlockDialog,
  SelectBuildingBlockDialogResult,
} from 'src/courses/select-building-block/useSelectBuildingBlockDialog';

const quasar = useQuasar();
const notifications = useNotifications();
const authStore = useAuthenticationStore();
const { downloadFile } = useContentDownload();
const { togglePageDiscussionDrawer } = useDiscussionDrawer();

const props = defineProps<{
  lessonId: string;
  courseId: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const course = ref<Course>();
const lesson = ref<Lesson>();
const courseTeachingUnits = ref<CourseTeachingUnit[]>([]);

const lastSavedNotes = ref<string>();
const performingSaveNotes = ref(false);
const canSaveNotes = computed(
  () => course.value?.notes !== lastSavedNotes.value
);

const splitterModel = ref(30);
const selectedPageId = ref<string>('');
const selectedPage = ref<Page>();
const performingCreatePage = ref<string[]>([]);
const performingDeletePage = ref<string[]>([]);
const performingDeleteMockup = ref<string[]>([]);
const performingRemovePageBuildingBlock = ref<string[]>([]);
const performingAddBuildingBlockToPage = ref(false);

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
          selectable: false,
          body: 'phase',
          children: [
            ...teachingPhase.pages.map((page) => {
              return {
                value: page.id,
                label: page.title,
                teachingPhaseId: teachingPhase.id,
                pageId: page.id,
                icon: 'mdi-monitor',
                header: 'page',
              };
            }),
            {
              header: 'add-page',
              teachingPhaseId: teachingPhase.id,
            },
          ],
        };
      }),
    };
  })
);

onMounted(() => {
  const fetchLessonPromise = lessonApi
    .getLesson(props.lessonId)
    .then((response) => {
      lesson.value = response.data;
    });

  const fetchCoursePromise = courseApi
    .getCourse(props.courseId)
    .then((response) => {
      course.value = response.data;
      lastSavedNotes.value = response.data.notes;
    });

  const fetchCourseStructurePromise = courseApi
    .getCourseStructure(props.courseId)
    .then((response) => {
      courseTeachingUnits.value = response.data;

      const pages = courseTeachingUnits.value
        .flatMap((teachingUnit) => teachingUnit.teachingPhases)
        .flatMap((teachingPhase) => teachingPhase.pages);

      if (pages.length > 0) {
        selectTeachingPhase(pages[0].id);
      }
    });

  withLoading(
    Promise.allSettled([
      fetchLessonPromise,
      fetchCoursePromise,
      fetchCourseStructurePromise,
    ]).finally(() => {
      initialized.value = true;
    }),
    loading
  );
});

function mockupUploadFactory(
  files: readonly File[]
): Promise<QUploaderFactoryObject> {
  const page = selectedPage.value;

  if (!page) {
    return Promise.reject();
  }

  return authStore.getAccessToken().then((accessToken) => {
    return {
      url: `${basePath}/pages/${page.id}/mockups`,
      headers: [
        {
          name: 'Authorization',
          value: 'Bearer ' + accessToken,
        },
      ],
      formFields: files.map((file) => {
        return {
          name: 'name',
          value: file.name,
        };
      }),
    };
  });
}

function selectTeachingPhase(pageId: string | undefined) {
  selectedPageId.value = pageId ?? '';

  if (!pageId) {
    selectedPage.value = undefined;
  }

  selectedPage.value = courseTeachingUnits.value
    .flatMap((teachingUnit) => teachingUnit.teachingPhases)
    .flatMap((teachingPhase) => teachingPhase.pages)
    .find((page) => page.id === selectedPageId.value);
}

function openEditTechnologyWishDialog() {
  quasar
    .dialog(
      stringPromptDialog('Technologiewunsch bearbeiten', 'Technologiewunsch', {
        ok: {
          label: 'Speichern',
        },
        isValid: isRequired,
        model: course.value?.technologyWish,
      })
    )
    .onOk((technologyWish) => {
      courseApi
        .editCourse(props.courseId, {
          technologyWish,
        })
        .then(() => {
          notifications.saved();

          if (course.value) {
            course.value.technologyWish = technologyWish;
          }
        })
        .catch((err) => {
          notifications.apiError(err);
        });
    });
}

function saveNotes() {
  return withLoading(
    courseApi
      .editCourse(props.courseId, {
        notes: course.value?.notes,
      })
      .then(() => {
        lastSavedNotes.value = course.value?.notes;
        notifications.saved();
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingSaveNotes
  );
}

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
              teachingPhase.pages.push({
                id: response.data,
                teachingPhaseId: teachingPhaseId,
                title,
                linkedPages: [],
                buildingBlocks: [],
                mockups: [],
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

function openEditPageTitle(page: Page) {
  quasar
    .dialog(
      stringPromptDialog('Titel bearbeiten', 'Titel', {
        ok: {
          label: 'Speichern',
        },
        isValid: isRequired,
        model: page.title,
      })
    )
    .onOk((payload) => {
      pageApi
        .editPage(page.id, {
          title: payload,
        })
        .then(() => {
          notifications.saved();

          page.title = payload;
        })
        .catch((err) => {
          notifications.apiError(err);
        });
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

function openDeleteMockupDialog(mockup: Mockup) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      mockupApi
        .deleteMockup(mockup.id)
        .then(() => {
          const page = selectedPage.value;

          if (page) {
            const index = page.mockups.indexOf(mockup);

            if (index >= 0) {
              page.mockups.splice(index, 1);
            }
          }

          notifications.deleted();
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingDeleteMockup,
      mockup.id
    );
  });
}

function openRemoveBuildingBlockDialog(pageBuildingBlock: PageBuildingBlock) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      pageApi
        .removeBuildingBlockFromPage(pageBuildingBlock.pageBuildingBlockId)
        .then(() => {
          const page = selectedPage.value;

          if (page) {
            const index = page.buildingBlocks.indexOf(pageBuildingBlock);

            if (index >= 0) {
              page.buildingBlocks.splice(index, 1);
            }
          }

          notifications.deleted();
        })
        .catch((err) => {
          notifications.apiError(err);
        }),
      performingRemovePageBuildingBlock,
      pageBuildingBlock.pageBuildingBlockId
    );
  });
}

function openSelectBuildingBlockDialog(pageId: string) {
  const technologyWish = course.value?.technologyWish;

  if (!technologyWish) {
    console.error('could not find technologyWish');
    return;
  }

  quasar
    .dialog(selectBuildingBlockDialog(technologyWish))
    .onOk((result: SelectBuildingBlockDialogResult) => {
      withLoading(
        pageApi
          .addBuildingBlockToPage(pageId, {
            buildingBlockId: result.buildingBlock.id,
          })
          .then((response) => {
            const page = selectedPage.value;

            if (page) {
              page.buildingBlocks.push({
                pageBuildingBlockId: response.data,
                buildingBlockId: result.buildingBlock.id,
                name: result.buildingBlock.name,
                releaseStatus: result.buildingBlock.releaseStatus,
                description: result.buildingBlock.description,
              });
            }

            notifications.success();
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingAddBuildingBlockToPage
      );
    });
}
</script>
