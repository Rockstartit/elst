import {CourseApi, MockupApi, PageApi} from "src/services/generated/openapi";
import {api, basePath} from "boot/axios";
import {apiConfiguration} from "src/services/index";

export const courseApi = new CourseApi(apiConfiguration, basePath, api);
export const pageApi = new PageApi(apiConfiguration, basePath, api);
export const mockupApi = new MockupApi(apiConfiguration, basePath, api);
