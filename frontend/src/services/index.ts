import { api, basePath } from 'boot/axios';
import {
  Configuration,
  CourseApi,
  CourseUnitApi,
  PageApi,
} from 'src/services/generated/openapi/courses';
import { BuildingBlockApi } from 'src/services/generated/openapi/building_blocks';
import { useAuth0 } from '@auth0/auth0-vue';

export const apiConfiguration = new Configuration({
  basePath,
  accessToken: () => {
    const auth0 = useAuth0();

    if (auth0.isAuthenticated.value) {
      return auth0.getAccessTokenSilently().catch(() => '');
    }

    return Promise.resolve('');
  },
});

export const courseApi = new CourseApi(apiConfiguration, basePath, api);
export const courseUnitApi = new CourseUnitApi(apiConfiguration, basePath, api);
export const pageApi = new PageApi(apiConfiguration, basePath, api);
export const buildingBlockApi = new BuildingBlockApi(
  apiConfiguration,
  basePath,
  api
);
