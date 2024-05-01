import {basePath} from 'boot/axios';
import {Configuration,} from 'src/services/generated/openapi';
import {useAuthenticationStore} from 'src/stores/authentication/store';

export const apiConfiguration = new Configuration({
  basePath,
  accessToken: () => useAuthenticationStore().getAccessToken(),
});





