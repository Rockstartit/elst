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


// May contain unused imports in some cases
// @ts-ignore
import { BuildingBlockPropertyType } from './building-block-property-type';

/**
 * 
 * @export
 * @interface CreateBuildingBlockPropertyRequest
 */
export interface CreateBuildingBlockPropertyRequest {
    /**
     * 
     * @type {string}
     * @memberof CreateBuildingBlockPropertyRequest
     */
    'key': string;
    /**
     * 
     * @type {BuildingBlockPropertyType}
     * @memberof CreateBuildingBlockPropertyRequest
     */
    'type': BuildingBlockPropertyType;
    /**
     * 
     * @type {string}
     * @memberof CreateBuildingBlockPropertyRequest
     */
    'displayName': string;
    /**
     * 
     * @type {string}
     * @memberof CreateBuildingBlockPropertyRequest
     */
    'description': string;
}



