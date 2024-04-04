import { api, basePath } from 'boot/axios';
import {
  Configuration,
  CourseApi,
  CourseUnitApi,
  PageApi,
} from 'src/services/generated/openapi/courses';
import {
  BuildingBlockApi,
  RequirementApi,
} from 'src/services/generated/openapi/building_blocks';
import { useAuthenticationStore } from 'src/stores/authentication/store';
import { FileApi } from 'src/services/generated/openapi/files';

export const apiConfiguration = new Configuration({
  basePath,
  accessToken: () => useAuthenticationStore().getAccessToken(),
});

export const courseApi = new CourseApi(apiConfiguration, basePath, api);
export const courseUnitApi = new CourseUnitApi(apiConfiguration, basePath, api);
export const pageApi = new PageApi(apiConfiguration, basePath, api);
export const buildingBlockApi = new BuildingBlockApi(
  apiConfiguration,
  basePath,
  api
);

export const requirementApi = new RequirementApi(
  apiConfiguration,
  basePath,
  api
);

export const fileApi = new FileApi(apiConfiguration, basePath, api);
