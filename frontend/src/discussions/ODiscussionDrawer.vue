<template>
  <q-drawer
    v-model="discussionDrawerOpen"
    side="right"
    overlay
    elevated
    :width="550">
    <div class="q-pa-md">
      <ODiscussions
        v-if="!selectedDiscussionId"
        :course-id="openCourseId"
        :page-id="openPageId"
        :mockup-id="openMockupId"
        @select="selectDiscussion">
        <template #action>
          <div class="col-auto">
            <TertiaryButton
              icon="mdi-close"
              dense
              flat
              @click="closeDiscussionDrawer" />
          </div>
        </template>
      </ODiscussions>

      <ODiscussionChat
        v-if="selectedDiscussionId"
        :discussion-id="selectedDiscussionId">
        <template #action>
          <div class="col-auto q-pr-md">
            <TertiaryButton
              icon="mdi-chevron-left"
              dense
              flat
              @click="unselectDiscussion" />
          </div>
        </template>
      </ODiscussionChat>
    </div>
  </q-drawer>
</template>

<script setup lang="ts">
import TertiaryButton from 'src/core/TertiaryButton.vue';
import ODiscussions from 'src/discussions/ODiscussions.vue';
import { useDiscussionDrawer } from 'src/discussions/useDiscussionDrawer';
import { ref } from 'vue';
import ODiscussionChat from 'src/discussions/ODiscussionChat.vue';

const {
  openPageId,
  openMockupId,
  openCourseId,
  discussionDrawerOpen,
  closeDiscussionDrawer,
} = useDiscussionDrawer();

const selectedDiscussionId = ref();

function selectDiscussion(discussionId: string) {
  selectedDiscussionId.value = discussionId;
}

function unselectDiscussion() {
  selectedDiscussionId.value = undefined;
}
</script>
