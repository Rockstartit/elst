import { BuildingBlockApi } from 'src/services/generated/openapi';
import { api, basePath } from 'boot/axios';
import { apiConfiguration } from 'src/services/index';

export const buildingBlockApi = new BuildingBlockApi(
  apiConfiguration,
  basePath,
  api
);
