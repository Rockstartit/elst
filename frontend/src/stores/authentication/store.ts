import { defineStore } from 'pinia';
import { computed } from 'vue';
import { useAuth0 } from '@auth0/auth0-vue';

export const useAuthenticationStore = defineStore('authentication', () => {
  const auth0 = useAuth0();

  const profilePicture = computed(() => {
    if (auth0.user.value?.picture) {
      return auth0.user.value?.picture as string;
    }

    return `https://api.dicebear.com/7.x/initials/svg?seed=${auth0.user.value.given_name ?? auth0.user.value.email}&radius=20&backgroundColor=d1d4f9&textColor=000000`;
  });

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
