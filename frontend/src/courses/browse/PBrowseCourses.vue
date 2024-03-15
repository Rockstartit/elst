<template>
  <PBase content-width="600px">
    <OCourseList>
      <template #item="{ course }">
        <MCourseOverview
          :course="course"
          clickable
          @click="
            viewCourse({ courseId: course.id, version: course.version })
          " />
      </template>
    </OCourseList>

    <div class="row justify-center q-mt-md">
      <PrimaryButton
        label="Neuer Kurs"
        :loading="performingCreateCourse"
        @click="createEmptyCourse" />
    </div>
  </PBase>
</template>

<script setup lang="ts">
import PBase from 'src/core/PBase.vue';
import OCourseList from 'src/courses/browse/OCourseList.vue';
import MCourseOverview from 'src/courses/browse/MCourseOverview.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { withLoading } from 'src/core/useWithLoading';
import { courseApi } from 'src/services';
import { uid } from 'quasar';
import { ref } from 'vue';
import { useAppRouter } from 'src/router/useAppRouter';

const { viewCourse } = useAppRouter();

const performingCreateCourse = ref(false);

function createEmptyCourse() {
  return withLoading(
    courseApi
      .createCourse({
        name: 'Neuer Kurs',
        code: uid().substring(0, 7),
      })
      .then((response) => {
        viewCourse(response.data);
      }),
    performingCreateCourse
  );
}
</script>
