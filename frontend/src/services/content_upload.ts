import {FileApi} from "src/services/generated/openapi";
import {api, basePath} from "boot/axios";
import {apiConfiguration} from "src/services/index";

export const fileApi = new FileApi(apiConfiguration, basePath, api);
