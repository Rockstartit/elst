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
import { BuildingBlockRequirement } from '../model';
// @ts-ignore
import { DocumentRequirementRequest } from '../model';
// @ts-ignore
import { EditRequirementRequest } from '../model';
/**
 * RequirementApi - axios parameter creator
 * @export
 */
export const RequirementApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @summary Delete requirement
         * @param {string} requirementId Id of requirement
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deleteRequirement: async (requirementId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'requirementId' is not null or undefined
            assertParamExists('deleteRequirement', 'requirementId', requirementId)
            const localVarPath = `/requirements/{requirementId}`
                .replace(`{${"requirementId"}}`, encodeURIComponent(String(requirementId)));
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
         * @summary Document requirement
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {DocumentRequirementRequest} documentRequirementRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        documentRequirement: async (buildingBlockId: string, version: number, documentRequirementRequest: DocumentRequirementRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'buildingBlockId' is not null or undefined
            assertParamExists('documentRequirement', 'buildingBlockId', buildingBlockId)
            // verify required parameter 'version' is not null or undefined
            assertParamExists('documentRequirement', 'version', version)
            // verify required parameter 'documentRequirementRequest' is not null or undefined
            assertParamExists('documentRequirement', 'documentRequirementRequest', documentRequirementRequest)
            const localVarPath = `/building-blocks/{buildingBlockId}/{version}/requirements`
                .replace(`{${"buildingBlockId"}}`, encodeURIComponent(String(buildingBlockId)))
                .replace(`{${"version"}}`, encodeURIComponent(String(version)));
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
            localVarRequestOptions.data = serializeDataIfNeeded(documentRequirementRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Edit requirement
         * @param {string} requirementId Id of requirement
         * @param {EditRequirementRequest} editRequirementRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editRequirement: async (requirementId: string, editRequirementRequest: EditRequirementRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'requirementId' is not null or undefined
            assertParamExists('editRequirement', 'requirementId', requirementId)
            // verify required parameter 'editRequirementRequest' is not null or undefined
            assertParamExists('editRequirement', 'editRequirementRequest', editRequirementRequest)
            const localVarPath = `/requirements/{requirementId}`
                .replace(`{${"requirementId"}}`, encodeURIComponent(String(requirementId)));
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
            localVarRequestOptions.data = serializeDataIfNeeded(editRequirementRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Get building block requirements
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getAllRequirements: async (buildingBlockId: string, version: number, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'buildingBlockId' is not null or undefined
            assertParamExists('getAllRequirements', 'buildingBlockId', buildingBlockId)
            // verify required parameter 'version' is not null or undefined
            assertParamExists('getAllRequirements', 'version', version)
            const localVarPath = `/building-blocks/{buildingBlockId}/{version}/requirements`
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
 * RequirementApi - functional programming interface
 * @export
 */
export const RequirementApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = RequirementApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @summary Delete requirement
         * @param {string} requirementId Id of requirement
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async deleteRequirement(requirementId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.deleteRequirement(requirementId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['RequirementApi.deleteRequirement']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Document requirement
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {DocumentRequirementRequest} documentRequirementRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async documentRequirement(buildingBlockId: string, version: number, documentRequirementRequest: DocumentRequirementRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<string>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.documentRequirement(buildingBlockId, version, documentRequirementRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['RequirementApi.documentRequirement']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Edit requirement
         * @param {string} requirementId Id of requirement
         * @param {EditRequirementRequest} editRequirementRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async editRequirement(requirementId: string, editRequirementRequest: EditRequirementRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.editRequirement(requirementId, editRequirementRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['RequirementApi.editRequirement']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Get building block requirements
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getAllRequirements(buildingBlockId: string, version: number, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<BuildingBlockRequirement>>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getAllRequirements(buildingBlockId, version, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['RequirementApi.getAllRequirements']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * RequirementApi - factory interface
 * @export
 */
export const RequirementApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = RequirementApiFp(configuration)
    return {
        /**
         * 
         * @summary Delete requirement
         * @param {string} requirementId Id of requirement
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deleteRequirement(requirementId: string, options?: any): AxiosPromise<void> {
            return localVarFp.deleteRequirement(requirementId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Document requirement
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {DocumentRequirementRequest} documentRequirementRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        documentRequirement(buildingBlockId: string, version: number, documentRequirementRequest: DocumentRequirementRequest, options?: any): AxiosPromise<string> {
            return localVarFp.documentRequirement(buildingBlockId, version, documentRequirementRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Edit requirement
         * @param {string} requirementId Id of requirement
         * @param {EditRequirementRequest} editRequirementRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editRequirement(requirementId: string, editRequirementRequest: EditRequirementRequest, options?: any): AxiosPromise<void> {
            return localVarFp.editRequirement(requirementId, editRequirementRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Get building block requirements
         * @param {string} buildingBlockId Id of building block
         * @param {number} version Version of building block
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getAllRequirements(buildingBlockId: string, version: number, options?: any): AxiosPromise<Array<BuildingBlockRequirement>> {
            return localVarFp.getAllRequirements(buildingBlockId, version, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * RequirementApi - object-oriented interface
 * @export
 * @class RequirementApi
 * @extends {BaseAPI}
 */
export class RequirementApi extends BaseAPI {
    /**
     * 
     * @summary Delete requirement
     * @param {string} requirementId Id of requirement
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof RequirementApi
     */
    public deleteRequirement(requirementId: string, options?: RawAxiosRequestConfig) {
        return RequirementApiFp(this.configuration).deleteRequirement(requirementId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Document requirement
     * @param {string} buildingBlockId Id of building block
     * @param {number} version Version of building block
     * @param {DocumentRequirementRequest} documentRequirementRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof RequirementApi
     */
    public documentRequirement(buildingBlockId: string, version: number, documentRequirementRequest: DocumentRequirementRequest, options?: RawAxiosRequestConfig) {
        return RequirementApiFp(this.configuration).documentRequirement(buildingBlockId, version, documentRequirementRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Edit requirement
     * @param {string} requirementId Id of requirement
     * @param {EditRequirementRequest} editRequirementRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof RequirementApi
     */
    public editRequirement(requirementId: string, editRequirementRequest: EditRequirementRequest, options?: RawAxiosRequestConfig) {
        return RequirementApiFp(this.configuration).editRequirement(requirementId, editRequirementRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Get building block requirements
     * @param {string} buildingBlockId Id of building block
     * @param {number} version Version of building block
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof RequirementApi
     */
    public getAllRequirements(buildingBlockId: string, version: number, options?: RawAxiosRequestConfig) {
        return RequirementApiFp(this.configuration).getAllRequirements(buildingBlockId, version, options).then((request) => request(this.axios, this.basePath));
    }
}

