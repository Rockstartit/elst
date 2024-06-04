import { api, basePath } from 'boot/axios';
import { Configuration, UserApi } from 'src/services/generated/openapi';
import { useAuthenticationStore } from 'src/stores/authentication/store';

export const apiConfiguration = new Configuration({
  basePath,
  accessToken: () => useAuthenticationStore().getAccessToken(),
});

export const userApi = new UserApi(apiConfiguration, basePath, api)




