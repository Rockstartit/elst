import {
  BuildingBlockApi,
  BuildingBlockMockupApi,
  BuildingBlockReadMeApi,
} from 'src/services/generated/openapi';
import { api, basePath } from 'boot/axios';
import { apiConfiguration } from 'src/services/index';

export const buildingBlockApi = new BuildingBlockApi(
  apiConfiguration,
  basePath,
  api
);

export const buildingBlockMockupApi = new BuildingBlockMockupApi(
  apiConfiguration,
  basePath,
  api
);

export const buildingBlockReadMeApi = new BuildingBlockReadMeApi(
  apiConfiguration,
  basePath,
  api
);
