import { defineStore } from 'pinia';
import { computed } from 'vue';
import { useAuth0 } from '@auth0/auth0-vue';

export const useAuthenticationStore = defineStore('authentication', () => {
  const auth0 = useAuth0();

  const profilePicture = computed(() => auth0.user.value?.picture as string);

  function getAccessToken(): Promise<string> {
    if (auth0.isAuthenticated.value) {
      return auth0.getAccessTokenSilently().catch(() => '');
    }

    return Promise.resolve('');
  }

  return {
    getAccessToken,
    profilePicture,
  };
});
