<template>
  <div>
    <p class="text-body2 text-weight-medium"> Verlinkte Seiten </p>

    <q-list>
      <MPageLink
        v-for="pageLink in linkedPages"
        :page-link="pageLink"
        :key="pageLink.targetPageId">
        <template #actions>
          <TertiaryButton
            icon="mdi-pencil-outline"
            dense
            flat
            @click="editPageLink(pageLink)" />

          <TertiaryButton
            icon="mdi-delete-outline"
            dense
            flat
            @click="openRemovePageLinkDialog(pageLink)" />
        </template>
      </MPageLink>

      <p v-if="linkedPages.length === 0" class="text-body2 text-grey-7">
        Keine Verlinkungen
      </p>
    </q-list>

    <PrimaryButton
      label="Neue Verlinkung"
      icon="mdi-link"
      dense
      flat
      text-color="primary"
      :loading="performingCreateLink"
      class="text-caption q-mt-md">
      <PageSelectionMenu
        :course-id="courseId"
        :exclude-page-ids="alreadyLinkedPageIds"
        @select="createPageLink" />
    </PrimaryButton>
  </div>
</template>

<script setup lang="ts">
import { PageLink, PageOverview } from 'src/services/generated/openapi';
import PrimaryButton from 'src/core/PrimaryButton.vue';
import MPageLink from 'src/courses/view-course/MPageLink.vue';
import PageSelectionMenu from 'src/courses/view-course/PageSelectionMenu.vue';
import { computed, ref } from 'vue';
import { withLoading, withLoadingArray } from 'src/core/useWithLoading';
import { pageApi } from 'src/services/course_conceptualization';
import { useNotifications } from 'src/core/useNotifications';
import TertiaryButton from 'src/core/TertiaryButton.vue';
import { useQuasar } from 'quasar';
import { confirmDialog, stringPromptDialog } from 'src/core/useBaseDialog';

const quasar = useQuasar();
const notifications = useNotifications();

const props = defineProps<{
  courseId: string;
  pageId: string;
}>();

const linkedPages = defineModel<PageLink[]>({ required: true });

const alreadyLinkedPageIds = computed(() => [
  props.pageId,
  ...linkedPages.value.map((pageLink) => pageLink.targetPageId),
]);

const performingCreateLink = ref(false);
const performingEditPageLink = ref<string[]>([]);
const performingRemovePageLink = ref<string[]>([]);

function createPageLink(page: PageOverview) {
  withLoading(
    pageApi
      .linkPages({
        pageId: props.pageId,
        targetPageId: page.id,
      })
      .then(() => {
        notifications.created();

        linkedPages.value.push({
          targetPageId: page.id,
          targetPageTitle: page.title,
        });
      })
      .catch((err) => {
        notifications.apiError(err);
      }),
    performingCreateLink
  );
}

function editPageLink(pageLink: PageLink) {
  quasar
    .dialog(
      stringPromptDialog('Verlinkung bearbeiten', 'Bedingung', {
        ok: {
          label: 'Speichern',
        },
        model: pageLink.condition,
        message: 'Beschreibe wann und wie zu der Seite verlinkt werden soll.',
      })
    )
    .onOk((condition) => {
      withLoadingArray(
        pageApi
          .editPageLink(props.pageId, pageLink.targetPageId, {
            condition,
          })
          .then(() => {
            notifications.saved();

            pageLink.condition = condition;
          })
          .catch((err) => {
            notifications.apiError(err);
          }),
        performingEditPageLink,
        pageLink.targetPageId
      );
    });
}

function openRemovePageLinkDialog(pageLink: PageLink) {
  quasar.dialog(confirmDialog()).onOk(() => {
    withLoadingArray(
      pageApi.removePageLink(props.pageId, pageLink.targetPageId).then(() => {
        notifications.deleted();

        const index = linkedPages.value.indexOf(pageLink);

        if (index >= 0) {
          linkedPages.value.splice(index, 1);
        }
      }),
      performingRemovePageLink,
      pageLink.targetPageId
    );
  });
}
</script>
