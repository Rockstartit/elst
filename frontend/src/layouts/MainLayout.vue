<template>
  <q-layout class="bg-white">
    <q-header bordered class="text-white bg-header" style="position: sticky">
      <q-toolbar class="q-py-sm q-px-md">
        <q-btn
          round
          dense
          flat
          :ripple="false"
          icon="mdi-school-outline"
          size="19px"
          color="white"
          class="q-mr-sm"
          no-caps
          :to="{ name: availableRoutes.browse_lessons }" />

        <div
          v-if="$q.screen.gt.sm"
          class="elst__toolbar-link q-ml-xs q-gutter-md text-body2 text-weight-bold row items-center no-wrap">
          <router-link :to="{ name: availableRoutes.browse_lessons }">
            <a class="text-white"> Unterrichte </a>
          </router-link>

          <router-link :to="{ name: availableRoutes.browse_building_blocks }">
            <a class="text-white"> Bausteine </a>
          </router-link>
        </div>

        <q-space />

        <div class="q-pl-sm q-gutter-md row items-center no-wrap">
          <q-btn dense flat round size="sm" icon="notifications" />

          <q-btn dense flat no-wrap>
            <q-avatar rounded size="20px" class="q-mr-xs">
              <img :src="profilePicture" alt="Profilbild" />
            </q-avatar>
            <q-icon name="arrow_drop_down" size="16px" />

            <q-menu auto-close>
              <q-list dense>
                <q-item>
                  <q-item-section>
                    <div
                      >Angemeldet als <strong>{{ authStore.name }}</strong></div
                    >
                  </q-item-section>
                </q-item>

                <q-separator />

                <q-item clickable @click="authStore.openEditUserProfileDialog">
                  <q-item-section>Profil bearbeiten</q-item-section>
                </q-item>
                <q-item clickable @click="authStore.logout">
                  <q-item-section>Abmelden</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </div>
      </q-toolbar>
    </q-header>

    <q-page-container style="padding-top: 0 !important">
      <div
        id="breadcrumbs"
        class="nav row q-py-sm q-px-lg"
        style="
          position: sticky;
          top: 62px;
          z-index: 2;
          transition: box-shadow 0.3s ease-in-out;
        ">
      </div>

      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script lang="ts" setup>
import { availableRoutes } from 'src/router/routes';
import { useAuthenticationStore } from 'stores/authentication/store';
import { computed } from 'vue';
import ODiscussionDrawer from 'src/discussions/ODiscussionDrawer.vue';

const authStore = useAuthenticationStore();

const profilePicture = computed(() => authStore.profilePicture);
</script>

<style lang="scss">
.elst {
  &__toolbar-link {
    a {
      color: white;
      text-decoration: none;
      &:hover {
        opacity: 0.7;
      }
    }
  }
}

@keyframes shadow-in {
  to {
    box-shadow: 0 5px 5px -3px rgba(0, 0, 0, 0.26);
  }
}

#breadcrumbs {
  background-color: white;
  animation: shadow-in linear both;
  animation-timeline: scroll();
  animation-range: 0 100px;
}
</style>
