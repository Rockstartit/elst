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
import { ReleaseStatus } from './release-status';

/**
 * 
 * @export
 * @interface PageBuildingBlock
 */
export interface PageBuildingBlock {
    /**
     * 
     * @type {string}
     * @memberof PageBuildingBlock
     */
    'pageBuildingBlockId': string;
    /**
     * 
     * @type {string}
     * @memberof PageBuildingBlock
     */
    'buildingBlockId': string;
    /**
     * 
     * @type {string}
     * @memberof PageBuildingBlock
     */
    'name': string;
    /**
     * 
     * @type {string}
     * @memberof PageBuildingBlock
     */
    'description'?: string;
    /**
     * 
     * @type {ReleaseStatus}
     * @memberof PageBuildingBlock
     */
    'releaseStatus': ReleaseStatus;
    /**
     * 
     * @type {number}
     * @memberof PageBuildingBlock
     */
    'order': number;
}



