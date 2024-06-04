<template>
  <div class="column">
    <div class="row items-center">
      <slot name="action" />

      <div class="col">
        <p class="text-h6 q-mb-none">
          {{ discussion?.title }}

          <q-badge
            v-if="discussion?.state === 'RESOLVED'"
            color="green"
            outline
            class="q-ml-sm">
            <q-icon name="mdi-check" class="q-mr-sm" size="1rem" />
            Abgeschlossen
          </q-badge>
        </p>
      </div>

      <div class="col-auto">
        <TertiaryButton icon="mdi-refresh" dense flat @click="refresh" />
      </div>
    </div>

    <div v-if="loading" class="row justify-center q-pa-md">
      <q-spinner size="24px" />
    </div>

    <div v-if="initialized && !loading" class="q-mt-md">
      <q-scroll-area style="height: calc(100dvh - 630px)">
        <div>
          <div
            v-if="comments.length === 0"
            class="row justify-center text-grey-7 bg-grey-1 q-py-md">
            <span> Starte die Diskussion mit einer ersten Nachricht. </span>
          </div>
          <q-chat-message
            v-for="comment in comments"
            :name="fullName(comment.createdBy)"
            :key="comment.id"
            :text="[comment.content]"
            :sent="comment.createdBy.id === authStore.userId" />
        </div>
      </q-scroll-area>

      <BaseInput v-model="newMessage" type="textarea" class="q-mt-xl" />

      <div class="row justify-end q-mt-sm" style="gap: 0.75rem">
        <PrimaryButton
          v-if="discussion?.state !== 'RESOLVED'"
          label="Diskussion abschließen"
          :color="newMessage.trim().length > 0 ? 'indigo-1' : undefined"
          :text-color="newMessage.trim().length > 0 ? 'indigo-10' : undefined"
          icon="mdi-check"
          @click="resolveDiscussion" />

        <PrimaryButton
          :disable="newMessage.trim().length === 0"
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
import { fullName } from 'src/core/useHelpers';

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
const performingResolveDiscussion = ref(false);

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

function resolveDiscussion() {
  return withLoading(
    discussionApi.resolveDiscussion(props.discussionId).then(() => {
      notifications.success();

      if (discussion.value) {
        discussion.value.state = 'RESOLVED';
      }
    }),
    performingResolveDiscussion
  );
}
</script>
