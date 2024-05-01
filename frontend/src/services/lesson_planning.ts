import {
  LearningMaterialApi,
  LessonApi,
  TeachingPhaseApi,
  TeachingUnitApi,
} from 'src/services/generated/openapi';
import { apiConfiguration } from 'src/services/index';
import { api, basePath } from 'boot/axios';

export const lessonApi = new LessonApi(apiConfiguration, basePath, api);
export const learningMaterialApi = new LearningMaterialApi(
  apiConfiguration,
  basePath,
  api
);
export const teachingUnitApi = new TeachingUnitApi(
  apiConfiguration,
  basePath,
  api
);
export const teachingPhaseApi = new TeachingPhaseApi(
  apiConfiguration,
  basePath,
  api
);
