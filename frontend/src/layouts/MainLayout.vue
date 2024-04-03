<template>
  <q-layout class="bg-grey-1">
    <q-header elevated class="text-white bg-header" height-hint="61.59">
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
          :to="{ name: availableRoutes.browse_courses }" />

        <div
          v-if="$q.screen.gt.sm"
          class="elst__toolbar-link q-ml-xs q-gutter-md text-body2 text-weight-bold row items-center no-wrap">
          <router-link :to="{ name: availableRoutes.browse_courses }">
            <a class="text-white"> Kurse </a>
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
              <img
                src="https://api.dicebear.com/7.x/initials/svg?seed=Daniel&radius=20&backgroundColor=d1d4f9&textColor=000000" />
            </q-avatar>
            <q-icon name="arrow_drop_down" size="16px" />

            <q-menu auto-close>
              <q-list dense>
                <q-item>
                  <q-item-section>
                    <div>Angemeldet als <strong>Daniel</strong></div>
                  </q-item-section>
                </q-item>

                <q-separator />

                <q-item clickable>
                  <q-item-section>Einstellungen</q-item-section>
                </q-item>
                <q-item clickable @click="logout()">
                  <q-item-section>Abmelden</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </div>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script lang="ts" setup>
import { availableRoutes } from 'src/router/routes';
import { useAuth0 } from '@auth0/auth0-vue';

const { logout } = useAuth0();
</script>

<style lang="sass">
.elst
  &__toolbar-link
    a
      color: white
      text-decoration: none
      &:hover
        opacity: 0.7
</style>
