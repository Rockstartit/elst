<template>
  <div class="column">
    <div class="row items-center">
      <slot name="action" />

      <div class="col">
        <p class="text-h6 q-mb-none"> Diskussion </p>
      </div>

      <div class="col-auto">
        <TertiaryButton icon="mdi-refresh" dense flat @click="refresh" />
      </div>
    </div>

    <div v-if="loading" class="row justify-center q-pa-md">
      <q-spinner size="24px" />
    </div>

    <div v-if="initialized && !loading" class="q-mt-md">
      <q-scroll-area style="height: calc(100dvh - 365px)">
        <div>
          <div
            v-if="comments.length === 0"
            class="row justify-center text-grey-7 bg-grey-1 q-py-md">
            <span> Starte die Diskussion mit einer ersten Nachricht. </span>
          </div>
          <q-chat-message
            v-for="comment in comments"
            :key="comment.id"
            :text="[comment.content]"
            :sent="comment.createdBy.id === authStore.userId" />
        </div>
      </q-scroll-area>

      <BaseInput v-model="newMessage" type="textarea" class="q-mt-xl" />

      <div class="row justify-end q-mt-sm">
        <PrimaryButton
          v-if="newMessage.trim().length > 0"
          label="Absenden"
          class="elst__base-button-width"
          @click="addComment" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { Comment, Discussion } from 'src/services/generated/openapi';
import { withLoading } from 'src/core/useWithLoading';
import { discussionApi } from 'src/services/collaboration';
import { useAuthenticationStore } from 'stores/authentication/store';
import BaseInput from 'src/core/BaseInput.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useNotifications } from 'src/core/useNotifications';
import TertiaryButton from 'src/core/TertiaryButton.vue';

const notifications = useNotifications();
const authStore = useAuthenticationStore();

const props = defineProps<{
  discussionId: string;
}>();

const initialized = ref(false);
const loading = ref(false);

const discussion = ref<Discussion>();
const comments = ref<Comment[]>([]);

const newMessage = ref('');
const performingAddComment = ref(false);

onMounted(() => {
  withLoading(
    refresh().finally(() => {
      initialized.value = true;
    }),
    loading
  );
});

function refresh() {
  const discussionPromise = discussionApi
    .getDiscussion(props.discussionId)
    .then((response) => {
      discussion.value = response.data;
    });

  const commentsPromise = discussionApi
    .getAllComments(props.discussionId)
    .then((response) => {
      comments.value = response.data;
    });

  return Promise.allSettled([discussionPromise, commentsPromise]);
}

function addComment() {
  return withLoading(
    discussionApi
      .addComment(props.discussionId, {
        content: newMessage.value,
      })
      .then((response) => {
        comments.value.push({
          id: response.data,
          content: newMessage.value,
          createdAt: new Date().toISOString(),
          createdBy: {
            id: authStore.userId ?? '',
            lastName: authStore.lastName,
            firstName: authStore.firstName,
          },
        });

        newMessage.value = '';
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingAddComment
  );
}
</script>
