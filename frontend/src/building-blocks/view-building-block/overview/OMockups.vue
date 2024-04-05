<template>
  <q-carousel v-bind="$props" v-model="slide" v-model:fullscreen="fullscreen">
    <q-carousel-slide
      v-for="(imgSrc, index) in imgSources"
      :key="'mockup-img-' + index"
      :name="index + 1"
      :img-src="imgSrc" />

    <template v-slot:control>
      <q-carousel-control position="bottom-right" :offset="[18, 18]">
        <q-btn
          push
          round
          dense
          color="white"
          text-color="primary"
          :icon="fullscreen ? 'fullscreen_exit' : 'fullscreen'"
          @click="fullscreen = !fullscreen" />
      </q-carousel-control>
    </template>
  </q-carousel>
</template>

<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {QCarouselProps} from 'quasar';
import {BuildingBlockVersion} from "src/services/generated/openapi/building_blocks";
import {useStorage} from "src/building-blocks/view-building-block/mockups/useStorage";
import {fileApi} from "src/services";

const { downloadMockupImage } = useStorage();

const props = withDefaults(defineProps<{
  buildingBlockVersion: BuildingBlockVersion
} & Omit<QCarouselProps, 'fullscreen'>>(), {
  swipeable: true,
  animated: true,
  thumbnails: true,
  infinite: true,
});

const slide = ref(1);
const fullscreen = ref(false);

const imgSources = ref<string[]>([])

onMounted(() => {
  fileApi.getBuildingBlockMockups(props.buildingBlockVersion.buildingBlockId, props.buildingBlockVersion.version).then(response => {
    return Promise.allSettled(response.data.map((mockupId) => downloadMockupImage(mockupId))).then(response => {
      imgSources.value = response.filter(result => result.status === 'fulfilled').map(result => {
        if (result.status === 'fulfilled') {
          return result.value;
        }

        return ''
      });
    })
  })
})
</script>
