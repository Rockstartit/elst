import { ref } from 'vue';

export const discussionDrawerOpen = ref(false);

const openCourseId = ref<string>();
const openPageId = ref<string>();
const openMockupId = ref<string>();

export function useDiscussionDrawer() {
  function toggleCourseDiscussionDrawer(courseId: string) {
    if (openCourseId.value !== courseId) {
      discussionDrawerOpen.value = true;

      openCourseId.value = courseId;
      openPageId.value = undefined;
      openMockupId.value = undefined;
    } else {
      closeDiscussionDrawer();
    }
  }

  function togglePageDiscussionDrawer(pageId: string) {
    if (openPageId.value !== pageId) {
      discussionDrawerOpen.value = true;

      openCourseId.value = undefined;
      openPageId.value = pageId;
      openMockupId.value = undefined;
    } else {
      closeDiscussionDrawer();
    }
  }

  function toggleMockupDiscussionDrawer(mockupId: string) {
    if (openMockupId.value !== mockupId) {
      discussionDrawerOpen.value = true;

      openCourseId.value = undefined;
      openPageId.value = undefined;
      openMockupId.value = mockupId;
    } else {
      closeDiscussionDrawer();
    }
  }

  function closeDiscussionDrawer() {
    discussionDrawerOpen.value = false;
    openPageId.value = undefined;
  }

  return {
    openPageId,
    openCourseId,
    openMockupId,
    discussionDrawerOpen,
    closeDiscussionDrawer,
    togglePageDiscussionDrawer,
    toggleCourseDiscussionDrawer,
    toggleMockupDiscussionDrawer,
  };
}
