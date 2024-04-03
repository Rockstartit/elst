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
          no-caps />

        <div
          class="q-ml-xs text-body2 text-weight-bold row items-center no-wrap">
          E-Learning Specification Tool (ELST)
        </div>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script lang="ts" setup></script>
