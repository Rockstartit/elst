import { defineStore } from 'pinia';
import { computed, onMounted, ref } from 'vue';
import { useAuth0 } from '@auth0/auth0-vue';
import { userApi } from 'src/services';
import { useQuasar } from 'quasar';
import {
  editUserProfileDialog,
  EditUserProfileDialogResult,
} from 'src/users/useEditUserProfileDialog';
import { UserProfile } from 'src/services/generated/openapi';
import { fullName } from 'src/core/useHelpers';

export const authorizationParams = {
  redirect_uri: window.location.origin + '/public/pub/callback',
};

export const useAuthenticationStore = defineStore('authentication', () => {
  const auth0 = useAuth0();
  const quasar = useQuasar();

  const userProfile = ref<UserProfile>();

  const profilePicture = computed(() => {
    if (auth0.user.value?.picture) {
      return auth0.user.value?.picture as string;
    }

    return `https://api.dicebear.com/7.x/initials/svg?seed=${auth0.user.value?.given_name ?? auth0.user.value?.email}&radius=20&backgroundColor=d1d4f9&textColor=000000`;
  });

  const name = computed(() => {
    const value = fullName({
      firstName: firstName.value,
      lastName: lastName.value,
    });

    if (!value) {
      return auth0.user.value?.email;
    }

    return value;
  });

  const firstName = computed(
    () => userProfile.value?.firstName ?? auth0.user.value?.given_name
  );

  const lastName = computed(
    () => userProfile.value?.lastName ?? auth0.user.value?.family_name
  );

  const userId = computed(() => auth0.user.value?.sub);

  onMounted(() => {
    getUserProfile().then(() => {
      if (userProfile.value && userProfileIsIncomplete(userProfile.value)) {
        openEditUserProfileDialog();
      }
    });
  });

  function openEditUserProfileDialog() {
    if (userProfile.value) {
      quasar
        .dialog(
          editUserProfileDialog({
            userProfile: {
              id: userProfile.value.id,
              firstName:
                userProfile.value?.firstName ?? auth0.user.value?.given_name,
              lastName:
                userProfile.value?.lastName ?? auth0.user.value?.family_name,
            },
          })
        )
        .onOk((result: EditUserProfileDialogResult) => {
          if (userProfile.value) {
            userProfile.value.firstName = result.firstName;
            userProfile.value.lastName = result.lastName;
          }
        });
    }
  }

  function getUserProfile() {
    const userIdToFetch = userId.value;

    if (!userIdToFetch) {
      return Promise.reject();
    }

    return userApi.getUserProfile(userIdToFetch).then((response) => {
      userProfile.value = response.data;
    });
  }

  function userProfileIsIncomplete(userProfile: UserProfile) {
    return userProfile.firstName === null && userProfile.lastName === null;
  }

  function getAccessToken(): Promise<string> {
    if (auth0.isAuthenticated.value) {
      return auth0.getAccessTokenSilently().catch(() => '');
    }

    return Promise.resolve('');
  }

  function login() {
    auth0.loginWithRedirect({
      authorizationParams,
    });
  }

  function logout() {
    auth0.logout({
      logoutParams: {
        redirect_uri: window.location.origin + '/public/pub/login',
      },
    });
  }

  return {
    name,
    login,
    logout,
    userId,
    lastName,
    firstName,
    userProfile,
    getAccessToken,
    profilePicture,
    openEditUserProfileDialog,
  };
});
