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
import { AddBuildingBlockToPageRequest } from '../model';
// @ts-ignore
import { EditBuildingBlockPropertyValueRequest } from '../model';
// @ts-ignore
import { PageBuildingBlockProperty } from '../model';
/**
 * PageBuildingBlockApi - axios parameter creator
 * @export
 */
export const PageBuildingBlockApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @summary Add building block to page
         * @param {string} pageId Id of page
         * @param {AddBuildingBlockToPageRequest} addBuildingBlockToPageRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        addBuildingBlockToPage: async (pageId: string, addBuildingBlockToPageRequest: AddBuildingBlockToPageRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'pageId' is not null or undefined
            assertParamExists('addBuildingBlockToPage', 'pageId', pageId)
            // verify required parameter 'addBuildingBlockToPageRequest' is not null or undefined
            assertParamExists('addBuildingBlockToPage', 'addBuildingBlockToPageRequest', addBuildingBlockToPageRequest)
            const localVarPath = `/pages/{pageId}/page-building-blocks`
                .replace(`{${"pageId"}}`, encodeURIComponent(String(pageId)));
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
            localVarRequestOptions.data = serializeDataIfNeeded(addBuildingBlockToPageRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Edit building block property
         * @param {string} pageBuildingBlockId Id of page building block
         * @param {string} key Key of property
         * @param {EditBuildingBlockPropertyValueRequest} editBuildingBlockPropertyValueRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editBuildingBlockProperty: async (pageBuildingBlockId: string, key: string, editBuildingBlockPropertyValueRequest: EditBuildingBlockPropertyValueRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'pageBuildingBlockId' is not null or undefined
            assertParamExists('editBuildingBlockProperty', 'pageBuildingBlockId', pageBuildingBlockId)
            // verify required parameter 'key' is not null or undefined
            assertParamExists('editBuildingBlockProperty', 'key', key)
            // verify required parameter 'editBuildingBlockPropertyValueRequest' is not null or undefined
            assertParamExists('editBuildingBlockProperty', 'editBuildingBlockPropertyValueRequest', editBuildingBlockPropertyValueRequest)
            const localVarPath = `/page-building-blocks/{pageBuildingBlockId}/properties/{key}`
                .replace(`{${"pageBuildingBlockId"}}`, encodeURIComponent(String(pageBuildingBlockId)))
                .replace(`{${"key"}}`, encodeURIComponent(String(key)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'PUT', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


    
            localVarHeaderParameter['Content-Type'] = 'application/json';

            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = serializeDataIfNeeded(editBuildingBlockPropertyValueRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Get building block properties
         * @param {string} pageBuildingBlockId Id of page building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getBuildingBlockProperties: async (pageBuildingBlockId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'pageBuildingBlockId' is not null or undefined
            assertParamExists('getBuildingBlockProperties', 'pageBuildingBlockId', pageBuildingBlockId)
            const localVarPath = `/page-building-blocks/{pageBuildingBlockId}/properties`
                .replace(`{${"pageBuildingBlockId"}}`, encodeURIComponent(String(pageBuildingBlockId)));
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
         * @summary Remove building block from page
         * @param {string} pageBuildingBlockId Id of page building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        removeBuildingBlockFromPage: async (pageBuildingBlockId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'pageBuildingBlockId' is not null or undefined
            assertParamExists('removeBuildingBlockFromPage', 'pageBuildingBlockId', pageBuildingBlockId)
            const localVarPath = `/page-building-blocks/{pageBuildingBlockId}`
                .replace(`{${"pageBuildingBlockId"}}`, encodeURIComponent(String(pageBuildingBlockId)));
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
    }
};

/**
 * PageBuildingBlockApi - functional programming interface
 * @export
 */
export const PageBuildingBlockApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = PageBuildingBlockApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @summary Add building block to page
         * @param {string} pageId Id of page
         * @param {AddBuildingBlockToPageRequest} addBuildingBlockToPageRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async addBuildingBlockToPage(pageId: string, addBuildingBlockToPageRequest: AddBuildingBlockToPageRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<string>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.addBuildingBlockToPage(pageId, addBuildingBlockToPageRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['PageBuildingBlockApi.addBuildingBlockToPage']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Edit building block property
         * @param {string} pageBuildingBlockId Id of page building block
         * @param {string} key Key of property
         * @param {EditBuildingBlockPropertyValueRequest} editBuildingBlockPropertyValueRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async editBuildingBlockProperty(pageBuildingBlockId: string, key: string, editBuildingBlockPropertyValueRequest: EditBuildingBlockPropertyValueRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.editBuildingBlockProperty(pageBuildingBlockId, key, editBuildingBlockPropertyValueRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['PageBuildingBlockApi.editBuildingBlockProperty']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Get building block properties
         * @param {string} pageBuildingBlockId Id of page building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getBuildingBlockProperties(pageBuildingBlockId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<PageBuildingBlockProperty>>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getBuildingBlockProperties(pageBuildingBlockId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['PageBuildingBlockApi.getBuildingBlockProperties']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Remove building block from page
         * @param {string} pageBuildingBlockId Id of page building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async removeBuildingBlockFromPage(pageBuildingBlockId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<string>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.removeBuildingBlockFromPage(pageBuildingBlockId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['PageBuildingBlockApi.removeBuildingBlockFromPage']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * PageBuildingBlockApi - factory interface
 * @export
 */
export const PageBuildingBlockApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = PageBuildingBlockApiFp(configuration)
    return {
        /**
         * 
         * @summary Add building block to page
         * @param {string} pageId Id of page
         * @param {AddBuildingBlockToPageRequest} addBuildingBlockToPageRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        addBuildingBlockToPage(pageId: string, addBuildingBlockToPageRequest: AddBuildingBlockToPageRequest, options?: any): AxiosPromise<string> {
            return localVarFp.addBuildingBlockToPage(pageId, addBuildingBlockToPageRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Edit building block property
         * @param {string} pageBuildingBlockId Id of page building block
         * @param {string} key Key of property
         * @param {EditBuildingBlockPropertyValueRequest} editBuildingBlockPropertyValueRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editBuildingBlockProperty(pageBuildingBlockId: string, key: string, editBuildingBlockPropertyValueRequest: EditBuildingBlockPropertyValueRequest, options?: any): AxiosPromise<void> {
            return localVarFp.editBuildingBlockProperty(pageBuildingBlockId, key, editBuildingBlockPropertyValueRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Get building block properties
         * @param {string} pageBuildingBlockId Id of page building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getBuildingBlockProperties(pageBuildingBlockId: string, options?: any): AxiosPromise<Array<PageBuildingBlockProperty>> {
            return localVarFp.getBuildingBlockProperties(pageBuildingBlockId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Remove building block from page
         * @param {string} pageBuildingBlockId Id of page building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        removeBuildingBlockFromPage(pageBuildingBlockId: string, options?: any): AxiosPromise<string> {
            return localVarFp.removeBuildingBlockFromPage(pageBuildingBlockId, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * PageBuildingBlockApi - object-oriented interface
 * @export
 * @class PageBuildingBlockApi
 * @extends {BaseAPI}
 */
export class PageBuildingBlockApi extends BaseAPI {
    /**
     * 
     * @summary Add building block to page
     * @param {string} pageId Id of page
     * @param {AddBuildingBlockToPageRequest} addBuildingBlockToPageRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PageBuildingBlockApi
     */
    public addBuildingBlockToPage(pageId: string, addBuildingBlockToPageRequest: AddBuildingBlockToPageRequest, options?: RawAxiosRequestConfig) {
        return PageBuildingBlockApiFp(this.configuration).addBuildingBlockToPage(pageId, addBuildingBlockToPageRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Edit building block property
     * @param {string} pageBuildingBlockId Id of page building block
     * @param {string} key Key of property
     * @param {EditBuildingBlockPropertyValueRequest} editBuildingBlockPropertyValueRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PageBuildingBlockApi
     */
    public editBuildingBlockProperty(pageBuildingBlockId: string, key: string, editBuildingBlockPropertyValueRequest: EditBuildingBlockPropertyValueRequest, options?: RawAxiosRequestConfig) {
        return PageBuildingBlockApiFp(this.configuration).editBuildingBlockProperty(pageBuildingBlockId, key, editBuildingBlockPropertyValueRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Get building block properties
     * @param {string} pageBuildingBlockId Id of page building block
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PageBuildingBlockApi
     */
    public getBuildingBlockProperties(pageBuildingBlockId: string, options?: RawAxiosRequestConfig) {
        return PageBuildingBlockApiFp(this.configuration).getBuildingBlockProperties(pageBuildingBlockId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Remove building block from page
     * @param {string} pageBuildingBlockId Id of page building block
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof PageBuildingBlockApi
     */
    public removeBuildingBlockFromPage(pageBuildingBlockId: string, options?: RawAxiosRequestConfig) {
        return PageBuildingBlockApiFp(this.configuration).removeBuildingBlockFromPage(pageBuildingBlockId, options).then((request) => request(this.axios, this.basePath));
    }
}

