<template>
  <div class="column">
    <OBaseAnimatedList
      :items="discussions"
      :fetching="fetchingDiscussions"
      :initialized="initialized">
      <template #item="{ item }">
        <q-item
          clickable
          class="elst__rounded"
          @click="$emit('select', item.id)">
          <q-item-section side>
            <q-icon name="mdi-chat-outline" />
          </q-item-section>
          <q-item-section>
            <q-item-label>
              {{ item.title }}
            </q-item-label>
            <q-item-label caption>
              Erstellt durch
              {{ fullName(item.createdBy) }}
            </q-item-label>
          </q-item-section>

          <q-item-section v-if="item.state === 'RESOLVED'" side>
            <q-badge color="green" outline>
              <q-icon name="mdi-check" class="q-mr-sm" size="1rem" />
              Abgeschlossen
            </q-badge>
          </q-item-section>
        </q-item>
      </template>

      <template #after>
        <div class="row justify-center">
          <PrimaryButton
            label="Neue Diskussion"
            class="elst__base-button-width"
            @click="openStartDiscussionDialog" />
        </div>
      </template>
    </OBaseAnimatedList>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import {
  DiscussionOverview,
  DiscussionState,
} from 'src/services/generated/openapi';
import { withLoading } from 'src/core/useWithLoading';
import { discussionApi } from 'src/services/collaboration';
import OBaseAnimatedList from 'src/core/OBaseAnimatedList.vue';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import { useQuasar } from 'quasar';
import { isRequired, stringPromptDialog } from 'src/core/useBaseDialog';
import { useAuthenticationStore } from 'stores/authentication/store';
import { useNotifications } from 'src/core/useNotifications';
import { fullName } from 'src/core/useHelpers';

const quasar = useQuasar();
const notifications = useNotifications();
const authStore = useAuthenticationStore();

const props = defineProps<{
  courseId?: string;
  pageId?: string;
  mockupId?: string;
}>();

defineEmits<{
  (e: 'select', discussionId: string): void;
}>();

const initialized = ref(false);
const fetchingDiscussions = ref(false);
const discussions = ref<DiscussionOverview[]>([]);

const performingStartDiscussion = ref(false);

watch(
  () =>
    [props.courseId, props.pageId, props.mockupId].filter(
      (value) => value !== undefined
    ),
  () => {
    initialized.value = false;

    fetchDiscussions().finally(() => {
      initialized.value = true;
    });
  },
  { immediate: true }
);

function fetchDiscussions() {
  return withLoading(
    discussionApi
      .getAllDiscussions(props.courseId, props.pageId, props.mockupId)
      .then((response) => {
        discussions.value = response.data;
      }),
    fetchingDiscussions
  );
}

function openStartDiscussionDialog() {
  quasar
    .dialog(
      stringPromptDialog('Diskussion starten', 'Thema', {
        ok: {
          label: 'Erstellen',
        },
        isValid: isRequired,
      })
    )
    .onOk((title) => {
      const userId = authStore.userId;

      if (!userId) {
        return;
      }

      withLoading(
        discussionApi
          .startDiscussion({
            title,
            courseId: props.courseId,
            pageId: props.pageId,
            mockupId: props.mockupId,
          })
          .then((response) => {
            discussions.value.push({
              id: response.data,
              createdBy: {
                id: authStore.userId ?? '',
                firstName: authStore.firstName,
                lastName: authStore.lastName,
              },
              state: DiscussionState.Open,
              title,
            });

            notifications.success();
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingStartDiscussion
      );
    });
}
</script>
