<template>
  <div class="row q-py-md">
    <p
      v-html="sanitizedContent"
      class="markdown-renderer"
      style="word-break: break-word">
    </p>
  </div>
</template>

<script lang="ts" setup>
import { computed } from 'vue';
import { marked } from 'marked';
import DOMPurify from 'dompurify';

const props = defineProps<{
  content: string;
}>();

const renderedContent = computed(() => {
  return marked(props.content);
});

const sanitizedContent = computed(() =>
  DOMPurify.sanitize(renderedContent.value)
);
</script>

<style lang="scss">
.markdown-renderer {
  h1 {
    font-size: 2rem;
    line-height: 2rem;
  }

  h2 {
    font-size: 1.5rem;
    line-height: 1.5rem;
  }

  h3 {
    font-size: 1rem;
    line-height: 1rem;
  }

  h4 {
    font-size: 0.75rem;
    line-height: 0.75rem;
  }
}
</style>
