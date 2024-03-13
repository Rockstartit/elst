import { api, basePath } from 'boot/axios';
import {
  Configuration,
  CourseApi,
} from 'src/services/generated/openapi/courses';
import { BuildingBlockApi } from 'src/services/generated/openapi/building_blocks';

export const apiConfiguration = new Configuration({
  basePath,
});

export const courseApi = new CourseApi(apiConfiguration, basePath, api);
export const buildingBlockApi = new BuildingBlockApi(
  apiConfiguration,
  basePath,
  api
);
