/* tslint:disable */
/* eslint-disable */
/**
 * Files
 * E-Learning Specification Tool Courses
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import type { Configuration } from './configuration';
import type { AxiosPromise, AxiosInstance, RawAxiosRequestConfig } from 'axios';
import globalAxios from 'axios';
// Some imports not used depending on template conditions
// @ts-ignore
import { DUMMY_BASE_URL, assertParamExists, setApiKeyToObject, setBasicAuthToObject, setBearerAuthToObject, setOAuthToObject, setSearchParams, serializeDataIfNeeded, toPathString, createRequestFunction } from './common';
import type { RequestArgs } from './base';
// @ts-ignore
import { BASE_PATH, COLLECTION_FORMATS, BaseAPI, RequiredError, operationServerMap } from './base';

/**
 * 
 * @export
 * @interface BuildingBlockVersion
 */
export interface BuildingBlockVersion {
    /**
     * 
     * @type {string}
     * @memberof BuildingBlockVersion
     */
    'buildingBlockId': string;
    /**
     * 
     * @type {number}
     * @memberof BuildingBlockVersion
     */
    'version': number;
}
/**
 * 
 * @export
 * @interface EditFileContentRequest
 */
export interface EditFileContentRequest {
    /**
     * 
     * @type {string}
     * @memberof EditFileContentRequest
     */
    'content': string;
}

/**
 * FileApi - axios parameter creator
 * @export
 */
export const FileApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @summary Edit building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {EditFileContentRequest} editFileContentRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editBuildingBlockReadMe: async (buildingBlockId: string, version: number, editFileContentRequest: EditFileContentRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'buildingBlockId' is not null or undefined
            assertParamExists('editBuildingBlockReadMe', 'buildingBlockId', buildingBlockId)
            // verify required parameter 'version' is not null or undefined
            assertParamExists('editBuildingBlockReadMe', 'version', version)
            // verify required parameter 'editFileContentRequest' is not null or undefined
            assertParamExists('editBuildingBlockReadMe', 'editFileContentRequest', editFileContentRequest)
            const localVarPath = `/building-blocks/{buildingBlockId}/{version}/readme`
                .replace(`{${"buildingBlockId"}}`, encodeURIComponent(String(buildingBlockId)))
                .replace(`{${"version"}}`, encodeURIComponent(String(version)));
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
            localVarRequestOptions.data = serializeDataIfNeeded(editFileContentRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Get building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getBuildingBlockReadMe: async (buildingBlockId: string, version: number, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'buildingBlockId' is not null or undefined
            assertParamExists('getBuildingBlockReadMe', 'buildingBlockId', buildingBlockId)
            // verify required parameter 'version' is not null or undefined
            assertParamExists('getBuildingBlockReadMe', 'version', version)
            const localVarPath = `/building-blocks/{buildingBlockId}/{version}/readme`
                .replace(`{${"buildingBlockId"}}`, encodeURIComponent(String(buildingBlockId)))
                .replace(`{${"version"}}`, encodeURIComponent(String(version)));
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
    }
};

/**
 * FileApi - functional programming interface
 * @export
 */
export const FileApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = FileApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @summary Edit building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {EditFileContentRequest} editFileContentRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async editBuildingBlockReadMe(buildingBlockId: string, version: number, editFileContentRequest: EditFileContentRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.editBuildingBlockReadMe(buildingBlockId, version, editFileContentRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['FileApi.editBuildingBlockReadMe']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Get building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getBuildingBlockReadMe(buildingBlockId: string, version: number, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<string>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getBuildingBlockReadMe(buildingBlockId, version, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['FileApi.getBuildingBlockReadMe']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * FileApi - factory interface
 * @export
 */
export const FileApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = FileApiFp(configuration)
    return {
        /**
         * 
         * @summary Edit building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {EditFileContentRequest} editFileContentRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editBuildingBlockReadMe(buildingBlockId: string, version: number, editFileContentRequest: EditFileContentRequest, options?: any): AxiosPromise<void> {
            return localVarFp.editBuildingBlockReadMe(buildingBlockId, version, editFileContentRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Get building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getBuildingBlockReadMe(buildingBlockId: string, version: number, options?: any): AxiosPromise<string> {
            return localVarFp.getBuildingBlockReadMe(buildingBlockId, version, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * FileApi - object-oriented interface
 * @export
 * @class FileApi
 * @extends {BaseAPI}
 */
export class FileApi extends BaseAPI {
    /**
     * 
     * @summary Edit building block readme
     * @param {string} buildingBlockId Id of building block
     * @param {number} version Version of building block
     * @param {EditFileContentRequest} editFileContentRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof FileApi
     */
    public editBuildingBlockReadMe(buildingBlockId: string, version: number, editFileContentRequest: EditFileContentRequest, options?: RawAxiosRequestConfig) {
        return FileApiFp(this.configuration).editBuildingBlockReadMe(buildingBlockId, version, editFileContentRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Get building block readme
     * @param {string} buildingBlockId Id of building block
     * @param {number} version Version of building block
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof FileApi
     */
    public getBuildingBlockReadMe(buildingBlockId: string, version: number, options?: RawAxiosRequestConfig) {
        return FileApiFp(this.configuration).getBuildingBlockReadMe(buildingBlockId, version, options).then((request) => request(this.axios, this.basePath));
    }
}



