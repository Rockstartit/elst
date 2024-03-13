import { Ref } from 'vue';

function withMinLoading<T>(promise: Promise<T>, delay = 60): Promise<T> {
  return Promise.all([
    new Promise((resolve) => {
      if (delay > 0) {
        setTimeout(() => resolve(null), delay);
      } else {
        resolve(null);
      }
    }),
    promise,
  ]).then((res) => {
    const result = res.slice(1);

    if (result.length === 1) {
      return result[0];
    }

    return result;
  }) as Promise<T>;
}

export function withLoading<T>(
  promise: Promise<T>,
  loading: Ref<boolean>,
  options?: { delay?: number; minLoading?: number }
): Promise<T> {
  const optionsWithDefaults = {
    delay: 100,
    minLoading: 0,
    ...options,
  };

  const cancelLoading = setTimeout(
    () => (loading.value = true),
    optionsWithDefaults.delay
  );

  return withMinLoading(promise, optionsWithDefaults.minLoading).finally(() => {
    if (cancelLoading) {
      clearTimeout(cancelLoading);
    }

    loading.value = false;
  });
}

export function withLoadingArray<T>(
  promise: Promise<T>,
  loading: Ref<string[]>,
  id: string,
  options = { delay: 100, minLoading: 0 }
): Promise<T> {
  const cancelLoading = setTimeout(() => loading.value.push(id), options.delay);

  return withMinLoading(promise, options.minLoading).finally(() => {
    if (cancelLoading) {
      clearTimeout(cancelLoading);
    }

    const index = loading.value.findIndex((loadingId) => loadingId === id);
    loading.value.splice(index, 1);
  });
}
