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
import { EditReadMeRequest } from '../model';
/**
 * BuildingBlockReadMeApi - axios parameter creator
 * @export
 */
export const BuildingBlockReadMeApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @summary Edit building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {EditReadMeRequest} editReadMeRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editBuildingBlockReadMe: async (buildingBlockId: string, editReadMeRequest: EditReadMeRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'buildingBlockId' is not null or undefined
            assertParamExists('editBuildingBlockReadMe', 'buildingBlockId', buildingBlockId)
            // verify required parameter 'editReadMeRequest' is not null or undefined
            assertParamExists('editBuildingBlockReadMe', 'editReadMeRequest', editReadMeRequest)
            const localVarPath = `/building-blocks/{buildingBlockId}/readme`
                .replace(`{${"buildingBlockId"}}`, encodeURIComponent(String(buildingBlockId)));
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
            localVarRequestOptions.data = serializeDataIfNeeded(editReadMeRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Get building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getBuildingBlockReadMe: async (buildingBlockId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'buildingBlockId' is not null or undefined
            assertParamExists('getBuildingBlockReadMe', 'buildingBlockId', buildingBlockId)
            const localVarPath = `/building-blocks/{buildingBlockId}/readme`
                .replace(`{${"buildingBlockId"}}`, encodeURIComponent(String(buildingBlockId)));
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
 * BuildingBlockReadMeApi - functional programming interface
 * @export
 */
export const BuildingBlockReadMeApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = BuildingBlockReadMeApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @summary Edit building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {EditReadMeRequest} editReadMeRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async editBuildingBlockReadMe(buildingBlockId: string, editReadMeRequest: EditReadMeRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.editBuildingBlockReadMe(buildingBlockId, editReadMeRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['BuildingBlockReadMeApi.editBuildingBlockReadMe']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Get building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getBuildingBlockReadMe(buildingBlockId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<string>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getBuildingBlockReadMe(buildingBlockId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['BuildingBlockReadMeApi.getBuildingBlockReadMe']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * BuildingBlockReadMeApi - factory interface
 * @export
 */
export const BuildingBlockReadMeApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = BuildingBlockReadMeApiFp(configuration)
    return {
        /**
         * 
         * @summary Edit building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {EditReadMeRequest} editReadMeRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editBuildingBlockReadMe(buildingBlockId: string, editReadMeRequest: EditReadMeRequest, options?: any): AxiosPromise<void> {
            return localVarFp.editBuildingBlockReadMe(buildingBlockId, editReadMeRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Get building block readme
         * @param {string} buildingBlockId Id of building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getBuildingBlockReadMe(buildingBlockId: string, options?: any): AxiosPromise<string> {
            return localVarFp.getBuildingBlockReadMe(buildingBlockId, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * BuildingBlockReadMeApi - object-oriented interface
 * @export
 * @class BuildingBlockReadMeApi
 * @extends {BaseAPI}
 */
export class BuildingBlockReadMeApi extends BaseAPI {
    /**
     * 
     * @summary Edit building block readme
     * @param {string} buildingBlockId Id of building block
     * @param {EditReadMeRequest} editReadMeRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof BuildingBlockReadMeApi
     */
    public editBuildingBlockReadMe(buildingBlockId: string, editReadMeRequest: EditReadMeRequest, options?: RawAxiosRequestConfig) {
        return BuildingBlockReadMeApiFp(this.configuration).editBuildingBlockReadMe(buildingBlockId, editReadMeRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Get building block readme
     * @param {string} buildingBlockId Id of building block
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof BuildingBlockReadMeApi
     */
    public getBuildingBlockReadMe(buildingBlockId: string, options?: RawAxiosRequestConfig) {
        return BuildingBlockReadMeApiFp(this.configuration).getBuildingBlockReadMe(buildingBlockId, options).then((request) => request(this.axios, this.basePath));
    }
}

