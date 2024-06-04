<template>
  <div>
    <ODiscussions
      v-if="!selectedDiscussionId"
      :course-id="courseId"
      :page-id="pageId"
      :mockup-id="mockupId"
      @select="selectDiscussion" />

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
</template>

<script setup lang="ts">
import TertiaryButton from 'src/core/TertiaryButton.vue';
import ODiscussionChat from 'src/discussions/ODiscussionChat.vue';
import ODiscussions from 'src/discussions/ODiscussions.vue';
import { ref } from 'vue';

defineProps<{
  courseId?: string;
  pageId?: string;
  mockupId?: string;
}>();

const selectedDiscussionId = ref();

function selectDiscussion(discussionId: string) {
  selectedDiscussionId.value = discussionId;
}

function unselectDiscussion() {
  selectedDiscussionId.value = undefined;
}
</script>
