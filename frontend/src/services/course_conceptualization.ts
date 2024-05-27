import {
  CourseApi,
  PageMockupApi,
  PageApi,
} from 'src/services/generated/openapi';
import { api, basePath } from 'boot/axios';
import { apiConfiguration } from 'src/services/index';

export const courseApi = new CourseApi(apiConfiguration, basePath, api);
export const pageApi = new PageApi(apiConfiguration, basePath, api);
export const pageMockupApi = new PageMockupApi(apiConfiguration, basePath, api);
