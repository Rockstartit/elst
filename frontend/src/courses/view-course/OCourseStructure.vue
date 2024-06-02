<template>
  <div>
    <p class="text-h6 text-weight-medium q-mb-sm"> Kursstruktur </p>
    <p class="text-body2 text-grey-7">
      Die Kursstruktur orientiert sich an den Unterrichtseinheiten aus der
      Unterrichtsplanung.
    </p>

    <div>
      <q-splitter v-model="splitterModel">
        <template v-slot:before>
          <q-scroll-area style="height: calc(100dvh - 200px)">
            <OCourseStructureTree
              v-model="courseTeachingUnits"
              :course-id="courseId"
              :selected-page-id="selectedPageId"
              @select-page="selectPage" />
          </q-scroll-area>
        </template>

        <template v-slot:after>
          <q-scroll-area style="height: calc(100dvh - 200px)">
            <OPage
              v-if="selectedPage"
              v-model="selectedPage"
              :course-id="courseId"
              :technology-wish="technologyWish" />
          </q-scroll-area>
        </template>
      </q-splitter>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { CourseTeachingUnit, Page } from 'src/services/generated/openapi';
import OCourseStructureTree from 'src/courses/view-course/OCourseStructureTree.vue';
import OPage from 'src/courses/view-course/OPage.vue';

defineProps<{
  courseId: string;
  technologyWish: string;
}>();

const courseTeachingUnits = defineModel<CourseTeachingUnit[]>({
  required: true,
});

const splitterModel = ref(30);

const selectedPageId = ref<string>('');
const selectedPage = ref<Page>();

onMounted(() => {
  const pages = courseTeachingUnits.value
    .flatMap((teachingUnit) => teachingUnit.teachingPhases)
    .flatMap((teachingPhase) => teachingPhase.pages);

  if (pages.length > 0) {
    selectPage(pages[0].id);
  }
});

function selectPage(pageId: string | undefined) {
  selectedPageId.value = pageId ?? '';

  if (!pageId) {
    selectedPage.value = undefined;
  }

  selectedPage.value = courseTeachingUnits.value
    .flatMap((teachingUnit) => teachingUnit.teachingPhases)
    .flatMap((teachingPhase) => teachingPhase.pages)
    .find((page) => page.id === selectedPageId.value);
}
</script>
