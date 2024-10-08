/* tslint:disable */
/* eslint-disable */
/**
 * merged spec
 * merged spec
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import type { Configuration } from '../configuration';
import type { AxiosPromise, AxiosInstance, RawAxiosRequestConfig } from 'axios';
import globalAxios from 'axios';
// Some imports not used depending on template conditions
// @ts-ignore
import { DUMMY_BASE_URL, assertParamExists, setApiKeyToObject, setBasicAuthToObject, setBearerAuthToObject, setOAuthToObject, setSearchParams, serializeDataIfNeeded, toPathString, createRequestFunction } from '../common';
// @ts-ignore
import { BASE_PATH, COLLECTION_FORMATS, RequestArgs, BaseAPI, RequiredError, operationServerMap } from '../base';
// @ts-ignore
import { AddCommentRequest } from '../model';
// @ts-ignore
import { Comment } from '../model';
// @ts-ignore
import { Discussion } from '../model';
// @ts-ignore
import { DiscussionOverview } from '../model';
// @ts-ignore
import { EditCommentRequest } from '../model';
// @ts-ignore
import { EditDiscussionRequest } from '../model';
// @ts-ignore
import { StartDiscussionRequest } from '../model';
/**
 * DiscussionApi - axios parameter creator
 * @export
 */
export const DiscussionApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @summary Add comment to discussion
         * @param {string} discussionId Id of discussion
         * @param {AddCommentRequest} addCommentRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        addComment: async (discussionId: string, addCommentRequest: AddCommentRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'discussionId' is not null or undefined
            assertParamExists('addComment', 'discussionId', discussionId)
            // verify required parameter 'addCommentRequest' is not null or undefined
            assertParamExists('addComment', 'addCommentRequest', addCommentRequest)
            const localVarPath = `/discussions/{discussionId}/comments`
                .replace(`{${"discussionId"}}`, encodeURIComponent(String(discussionId)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'POST', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


    
            localVarHeaderParameter['Content-Type'] = 'application/json';

            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = serializeDataIfNeeded(addCommentRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Delete comment
         * @param {string} commentId Id of comment
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deleteComment: async (commentId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'commentId' is not null or undefined
            assertParamExists('deleteComment', 'commentId', commentId)
            const localVarPath = `/comments/{commentId}`
                .replace(`{${"commentId"}}`, encodeURIComponent(String(commentId)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'DELETE', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Edit comment
         * @param {string} commentId Id of comment
         * @param {EditCommentRequest} editCommentRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editComment: async (commentId: string, editCommentRequest: EditCommentRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'commentId' is not null or undefined
            assertParamExists('editComment', 'commentId', commentId)
            // verify required parameter 'editCommentRequest' is not null or undefined
            assertParamExists('editComment', 'editCommentRequest', editCommentRequest)
            const localVarPath = `/comments/{commentId}`
                .replace(`{${"commentId"}}`, encodeURIComponent(String(commentId)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'PATCH', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


    
            localVarHeaderParameter['Content-Type'] = 'application/json';

            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = serializeDataIfNeeded(editCommentRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Edit discussion
         * @param {string} discussionId Id of discussion
         * @param {EditDiscussionRequest} editDiscussionRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editDiscussion: async (discussionId: string, editDiscussionRequest: EditDiscussionRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'discussionId' is not null or undefined
            assertParamExists('editDiscussion', 'discussionId', discussionId)
            // verify required parameter 'editDiscussionRequest' is not null or undefined
            assertParamExists('editDiscussion', 'editDiscussionRequest', editDiscussionRequest)
            const localVarPath = `/discussions/{discussionId}`
                .replace(`{${"discussionId"}}`, encodeURIComponent(String(discussionId)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'PATCH', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


    
            localVarHeaderParameter['Content-Type'] = 'application/json';

            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = serializeDataIfNeeded(editDiscussionRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Get all comments in discussion
         * @param {string} discussionId Id of discussion
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getAllComments: async (discussionId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'discussionId' is not null or undefined
            assertParamExists('getAllComments', 'discussionId', discussionId)
            const localVarPath = `/discussions/{discussionId}/comments`
                .replace(`{${"discussionId"}}`, encodeURIComponent(String(discussionId)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Get all discussions
         * @param {string} [buildingBlockId] Id of building block
         * @param {string} [pageId] Id of page
         * @param {string} [mockupId] Id of mockup
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getAllDiscussions: async (buildingBlockId?: string, pageId?: string, mockupId?: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            const localVarPath = `/discussions`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)

            if (buildingBlockId !== undefined) {
                localVarQueryParameter['buildingBlockId'] = buildingBlockId;
            }

            if (pageId !== undefined) {
                localVarQueryParameter['pageId'] = pageId;
            }

            if (mockupId !== undefined) {
                localVarQueryParameter['mockupId'] = mockupId;
            }


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Get discussion
         * @param {string} discussionId Id of discussion
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getDiscussion: async (discussionId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'discussionId' is not null or undefined
            assertParamExists('getDiscussion', 'discussionId', discussionId)
            const localVarPath = `/discussions/{discussionId}`
                .replace(`{${"discussionId"}}`, encodeURIComponent(String(discussionId)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Resolve discussion
         * @param {string} discussionId Id of discussion
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        resolveDiscussion: async (discussionId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'discussionId' is not null or undefined
            assertParamExists('resolveDiscussion', 'discussionId', discussionId)
            const localVarPath = `/discussions/{discussionId}`
                .replace(`{${"discussionId"}}`, encodeURIComponent(String(discussionId)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'POST', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Start new discussion
         * @param {StartDiscussionRequest} startDiscussionRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        startDiscussion: async (startDiscussionRequest: StartDiscussionRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'startDiscussionRequest' is not null or undefined
            assertParamExists('startDiscussion', 'startDiscussionRequest', startDiscussionRequest)
            const localVarPath = `/discussions`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'POST', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


    
            localVarHeaderParameter['Content-Type'] = 'application/json';

            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = serializeDataIfNeeded(startDiscussionRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * DiscussionApi - functional programming interface
 * @export
 */
export const DiscussionApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = DiscussionApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @summary Add comment to discussion
         * @param {string} discussionId Id of discussion
         * @param {AddCommentRequest} addCommentRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async addComment(discussionId: string, addCommentRequest: AddCommentRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<string>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.addComment(discussionId, addCommentRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['DiscussionApi.addComment']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Delete comment
         * @param {string} commentId Id of comment
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async deleteComment(commentId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.deleteComment(commentId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['DiscussionApi.deleteComment']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Edit comment
         * @param {string} commentId Id of comment
         * @param {EditCommentRequest} editCommentRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async editComment(commentId: string, editCommentRequest: EditCommentRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.editComment(commentId, editCommentRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['DiscussionApi.editComment']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Edit discussion
         * @param {string} discussionId Id of discussion
         * @param {EditDiscussionRequest} editDiscussionRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async editDiscussion(discussionId: string, editDiscussionRequest: EditDiscussionRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.editDiscussion(discussionId, editDiscussionRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['DiscussionApi.editDiscussion']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Get all comments in discussion
         * @param {string} discussionId Id of discussion
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getAllComments(discussionId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<Comment>>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getAllComments(discussionId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['DiscussionApi.getAllComments']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Get all discussions
         * @param {string} [buildingBlockId] Id of building block
         * @param {string} [pageId] Id of page
         * @param {string} [mockupId] Id of mockup
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getAllDiscussions(buildingBlockId?: string, pageId?: string, mockupId?: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<DiscussionOverview>>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getAllDiscussions(buildingBlockId, pageId, mockupId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['DiscussionApi.getAllDiscussions']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Get discussion
         * @param {string} discussionId Id of discussion
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getDiscussion(discussionId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Discussion>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getDiscussion(discussionId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['DiscussionApi.getDiscussion']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Resolve discussion
         * @param {string} discussionId Id of discussion
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async resolveDiscussion(discussionId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.resolveDiscussion(discussionId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['DiscussionApi.resolveDiscussion']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Start new discussion
         * @param {StartDiscussionRequest} startDiscussionRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async startDiscussion(startDiscussionRequest: StartDiscussionRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<string>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.startDiscussion(startDiscussionRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['DiscussionApi.startDiscussion']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * DiscussionApi - factory interface
 * @export
 */
export const DiscussionApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = DiscussionApiFp(configuration)
    return {
        /**
         * 
         * @summary Add comment to discussion
         * @param {string} discussionId Id of discussion
         * @param {AddCommentRequest} addCommentRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        addComment(discussionId: string, addCommentRequest: AddCommentRequest, options?: any): AxiosPromise<string> {
            return localVarFp.addComment(discussionId, addCommentRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Delete comment
         * @param {string} commentId Id of comment
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deleteComment(commentId: string, options?: any): AxiosPromise<void> {
            return localVarFp.deleteComment(commentId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Edit comment
         * @param {string} commentId Id of comment
         * @param {EditCommentRequest} editCommentRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editComment(commentId: string, editCommentRequest: EditCommentRequest, options?: any): AxiosPromise<void> {
            return localVarFp.editComment(commentId, editCommentRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Edit discussion
         * @param {string} discussionId Id of discussion
         * @param {EditDiscussionRequest} editDiscussionRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editDiscussion(discussionId: string, editDiscussionRequest: EditDiscussionRequest, options?: any): AxiosPromise<void> {
            return localVarFp.editDiscussion(discussionId, editDiscussionRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Get all comments in discussion
         * @param {string} discussionId Id of discussion
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getAllComments(discussionId: string, options?: any): AxiosPromise<Array<Comment>> {
            return localVarFp.getAllComments(discussionId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Get all discussions
         * @param {string} [buildingBlockId] Id of building block
         * @param {string} [pageId] Id of page
         * @param {string} [mockupId] Id of mockup
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getAllDiscussions(buildingBlockId?: string, pageId?: string, mockupId?: string, options?: any): AxiosPromise<Array<DiscussionOverview>> {
            return localVarFp.getAllDiscussions(buildingBlockId, pageId, mockupId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Get discussion
         * @param {string} discussionId Id of discussion
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getDiscussion(discussionId: string, options?: any): AxiosPromise<Discussion> {
            return localVarFp.getDiscussion(discussionId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Resolve discussion
         * @param {string} discussionId Id of discussion
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        resolveDiscussion(discussionId: string, options?: any): AxiosPromise<void> {
            return localVarFp.resolveDiscussion(discussionId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Start new discussion
         * @param {StartDiscussionRequest} startDiscussionRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        startDiscussion(startDiscussionRequest: StartDiscussionRequest, options?: any): AxiosPromise<string> {
            return localVarFp.startDiscussion(startDiscussionRequest, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * DiscussionApi - object-oriented interface
 * @export
 * @class DiscussionApi
 * @extends {BaseAPI}
 */
export class DiscussionApi extends BaseAPI {
    /**
     * 
     * @summary Add comment to discussion
     * @param {string} discussionId Id of discussion
     * @param {AddCommentRequest} addCommentRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof DiscussionApi
     */
    public addComment(discussionId: string, addCommentRequest: AddCommentRequest, options?: RawAxiosRequestConfig) {
        return DiscussionApiFp(this.configuration).addComment(discussionId, addCommentRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Delete comment
     * @param {string} commentId Id of comment
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof DiscussionApi
     */
    public deleteComment(commentId: string, options?: RawAxiosRequestConfig) {
        return DiscussionApiFp(this.configuration).deleteComment(commentId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Edit comment
     * @param {string} commentId Id of comment
     * @param {EditCommentRequest} editCommentRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof DiscussionApi
     */
    public editComment(commentId: string, editCommentRequest: EditCommentRequest, options?: RawAxiosRequestConfig) {
        return DiscussionApiFp(this.configuration).editComment(commentId, editCommentRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Edit discussion
     * @param {string} discussionId Id of discussion
     * @param {EditDiscussionRequest} editDiscussionRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof DiscussionApi
     */
    public editDiscussion(discussionId: string, editDiscussionRequest: EditDiscussionRequest, options?: RawAxiosRequestConfig) {
        return DiscussionApiFp(this.configuration).editDiscussion(discussionId, editDiscussionRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Get all comments in discussion
     * @param {string} discussionId Id of discussion
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof DiscussionApi
     */
    public getAllComments(discussionId: string, options?: RawAxiosRequestConfig) {
        return DiscussionApiFp(this.configuration).getAllComments(discussionId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Get all discussions
     * @param {string} [buildingBlockId] Id of building block
     * @param {string} [pageId] Id of page
     * @param {string} [mockupId] Id of mockup
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof DiscussionApi
     */
    public getAllDiscussions(buildingBlockId?: string, pageId?: string, mockupId?: string, options?: RawAxiosRequestConfig) {
        return DiscussionApiFp(this.configuration).getAllDiscussions(buildingBlockId, pageId, mockupId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Get discussion
     * @param {string} discussionId Id of discussion
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof DiscussionApi
     */
    public getDiscussion(discussionId: string, options?: RawAxiosRequestConfig) {
        return DiscussionApiFp(this.configuration).getDiscussion(discussionId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Resolve discussion
     * @param {string} discussionId Id of discussion
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof DiscussionApi
     */
    public resolveDiscussion(discussionId: string, options?: RawAxiosRequestConfig) {
        return DiscussionApiFp(this.configuration).resolveDiscussion(discussionId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Start new discussion
     * @param {StartDiscussionRequest} startDiscussionRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof DiscussionApi
     */
    public startDiscussion(startDiscussionRequest: StartDiscussionRequest, options?: RawAxiosRequestConfig) {
        return DiscussionApiFp(this.configuration).startDiscussion(startDiscussionRequest, options).then((request) => request(this.axios, this.basePath));
    }
}

