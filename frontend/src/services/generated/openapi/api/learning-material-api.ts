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
import { EditLearningMaterialRequest } from '../model';
/**
 * LearningMaterialApi - axios parameter creator
 * @export
 */
export const LearningMaterialApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @summary Delete learning material
         * @param {string} learningMaterialId ID of learning material
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deleteLearningMaterial: async (learningMaterialId: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'learningMaterialId' is not null or undefined
            assertParamExists('deleteLearningMaterial', 'learningMaterialId', learningMaterialId)
            const localVarPath = `/learning-materials/{learningMaterialId}`
                .replace(`{${"learningMaterialId"}}`, encodeURIComponent(String(learningMaterialId)));
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
         * @summary Edit learning material
         * @param {string} learningMaterialId ID of learning material
         * @param {EditLearningMaterialRequest} editLearningMaterialRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editLearningMaterial: async (learningMaterialId: string, editLearningMaterialRequest: EditLearningMaterialRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'learningMaterialId' is not null or undefined
            assertParamExists('editLearningMaterial', 'learningMaterialId', learningMaterialId)
            // verify required parameter 'editLearningMaterialRequest' is not null or undefined
            assertParamExists('editLearningMaterial', 'editLearningMaterialRequest', editLearningMaterialRequest)
            const localVarPath = `/learning-materials/{learningMaterialId}`
                .replace(`{${"learningMaterialId"}}`, encodeURIComponent(String(learningMaterialId)));
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
            localVarRequestOptions.data = serializeDataIfNeeded(editLearningMaterialRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @summary Upload learning material
         * @param {string} teachingPhaseId ID of teaching phase
         * @param {string} [name] 
         * @param {File} [fileName] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        uploadLearningMaterial: async (teachingPhaseId: string, name?: string, fileName?: File, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'teachingPhaseId' is not null or undefined
            assertParamExists('uploadLearningMaterial', 'teachingPhaseId', teachingPhaseId)
            const localVarPath = `/teaching-phases/{teachingPhaseId}/learning-materials`
                .replace(`{${"teachingPhaseId"}}`, encodeURIComponent(String(teachingPhaseId)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'POST', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;
            const localVarFormParams = new ((configuration && configuration.formDataCtor) || FormData)();

            // authentication bearerAuth required
            // http bearer authentication required
            await setBearerAuthToObject(localVarHeaderParameter, configuration)


            if (name !== undefined) { 
                localVarFormParams.append('name', name as any);
            }
    
            if (fileName !== undefined) { 
                localVarFormParams.append('fileName', fileName as any);
            }
    
    
            localVarHeaderParameter['Content-Type'] = 'multipart/form-data';
    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = localVarFormParams;

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * LearningMaterialApi - functional programming interface
 * @export
 */
export const LearningMaterialApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = LearningMaterialApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @summary Delete learning material
         * @param {string} learningMaterialId ID of learning material
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async deleteLearningMaterial(learningMaterialId: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.deleteLearningMaterial(learningMaterialId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['LearningMaterialApi.deleteLearningMaterial']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Edit learning material
         * @param {string} learningMaterialId ID of learning material
         * @param {EditLearningMaterialRequest} editLearningMaterialRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async editLearningMaterial(learningMaterialId: string, editLearningMaterialRequest: EditLearningMaterialRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.editLearningMaterial(learningMaterialId, editLearningMaterialRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['LearningMaterialApi.editLearningMaterial']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @summary Upload learning material
         * @param {string} teachingPhaseId ID of teaching phase
         * @param {string} [name] 
         * @param {File} [fileName] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async uploadLearningMaterial(teachingPhaseId: string, name?: string, fileName?: File, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<string>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.uploadLearningMaterial(teachingPhaseId, name, fileName, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['LearningMaterialApi.uploadLearningMaterial']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * LearningMaterialApi - factory interface
 * @export
 */
export const LearningMaterialApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = LearningMaterialApiFp(configuration)
    return {
        /**
         * 
         * @summary Delete learning material
         * @param {string} learningMaterialId ID of learning material
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        deleteLearningMaterial(learningMaterialId: string, options?: any): AxiosPromise<void> {
            return localVarFp.deleteLearningMaterial(learningMaterialId, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Edit learning material
         * @param {string} learningMaterialId ID of learning material
         * @param {EditLearningMaterialRequest} editLearningMaterialRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        editLearningMaterial(learningMaterialId: string, editLearningMaterialRequest: EditLearningMaterialRequest, options?: any): AxiosPromise<void> {
            return localVarFp.editLearningMaterial(learningMaterialId, editLearningMaterialRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @summary Upload learning material
         * @param {string} teachingPhaseId ID of teaching phase
         * @param {string} [name] 
         * @param {File} [fileName] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        uploadLearningMaterial(teachingPhaseId: string, name?: string, fileName?: File, options?: any): AxiosPromise<string> {
            return localVarFp.uploadLearningMaterial(teachingPhaseId, name, fileName, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * LearningMaterialApi - object-oriented interface
 * @export
 * @class LearningMaterialApi
 * @extends {BaseAPI}
 */
export class LearningMaterialApi extends BaseAPI {
    /**
     * 
     * @summary Delete learning material
     * @param {string} learningMaterialId ID of learning material
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof LearningMaterialApi
     */
    public deleteLearningMaterial(learningMaterialId: string, options?: RawAxiosRequestConfig) {
        return LearningMaterialApiFp(this.configuration).deleteLearningMaterial(learningMaterialId, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Edit learning material
     * @param {string} learningMaterialId ID of learning material
     * @param {EditLearningMaterialRequest} editLearningMaterialRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof LearningMaterialApi
     */
    public editLearningMaterial(learningMaterialId: string, editLearningMaterialRequest: EditLearningMaterialRequest, options?: RawAxiosRequestConfig) {
        return LearningMaterialApiFp(this.configuration).editLearningMaterial(learningMaterialId, editLearningMaterialRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @summary Upload learning material
     * @param {string} teachingPhaseId ID of teaching phase
     * @param {string} [name] 
     * @param {File} [fileName] 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof LearningMaterialApi
     */
    public uploadLearningMaterial(teachingPhaseId: string, name?: string, fileName?: File, options?: RawAxiosRequestConfig) {
        return LearningMaterialApiFp(this.configuration).uploadLearningMaterial(teachingPhaseId, name, fileName, options).then((request) => request(this.axios, this.basePath));
    }
}

