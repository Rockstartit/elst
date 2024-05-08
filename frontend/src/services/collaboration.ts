import { DiscussionApi } from 'src/services/generated/openapi';
import { apiConfiguration } from 'src/services/index';
import { api, basePath } from 'boot/axios';

export const discussionApi = new DiscussionApi(apiConfiguration, basePath, api);
