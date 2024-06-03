import {
  CourseApi,
  PageMockupApi,
  PageApi,
  PageBuildingBlockApi,
} from 'src/services/generated/openapi';
import { api, basePath } from 'boot/axios';
import { apiConfiguration } from 'src/services/index';

export const courseApi = new CourseApi(apiConfiguration, basePath, api);
export const pageApi = new PageApi(apiConfiguration, basePath, api);
export const pageMockupApi = new PageMockupApi(apiConfiguration, basePath, api);
export const pageBuildingBlockApi = new PageBuildingBlockApi(
  apiConfiguration,
  basePath,
  api
);
