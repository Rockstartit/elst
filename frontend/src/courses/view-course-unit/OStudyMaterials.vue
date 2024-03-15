<template>
  <q-list dense>
    <q-item>
      <q-item-section>
        <q-item-label class="text-weight-medium"> Materialien </q-item-label>
      </q-item-section>
    </q-item>

    <q-item-label v-if="!studyMaterials" header class="text-grey-7">
      Keine Angabe
    </q-item-label>

    <q-list v-if="studyMaterials" dense>
      <q-item v-if="studyMaterials.bibliography">
        <q-item-section side>
          <q-icon name="mdi-book-open-outline" />
        </q-item-section>
        <q-item-section>
          <q-item-label>
            {{ studyMaterials.bibliography }}
          </q-item-label>
        </q-item-section>
      </q-item>

      <q-item v-if="studyMaterials.eBook">
        <q-item-section side>
          <q-icon name="mdi-book-outline" />
        </q-item-section>
        <q-item-section>
          <q-item-label>
            {{ studyMaterials.eBook }}
          </q-item-label>
        </q-item-section>
      </q-item>

      <q-item v-if="studyMaterials.eReading">
        <q-item-section side>
          <q-icon name="mdi-cellphone-screenshot" />
        </q-item-section>
        <q-item-section>
          <q-item-label>
            {{ studyMaterials.eReading }}
          </q-item-label>
        </q-item-section>
      </q-item>

      <q-item v-if="studyMaterials.relatedLinks">
        <q-item-section side>
          <q-icon name="mdi-web" />
        </q-item-section>
        <q-item-section>
          <q-item-label>
            {{ studyMaterials.relatedLinks }}
          </q-item-label>
        </q-item-section>
      </q-item>
    </q-list>
  </q-list>
</template>

<script lang="ts" setup>
import { StudyMaterials } from 'src/services/generated/openapi/courses';

defineProps<{
  studyMaterials: StudyMaterials | undefined;
}>();
</script>
