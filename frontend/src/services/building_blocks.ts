import {BuildingBlockApi, RequirementApi} from "src/services/generated/openapi";
import {api, basePath} from "boot/axios";
import {apiConfiguration} from "src/services/index";

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
