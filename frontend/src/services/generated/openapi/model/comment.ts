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
import { User } from './user';

/**
 * 
 * @export
 * @interface Comment
 */
export interface Comment {
    /**
     * 
     * @type {string}
     * @memberof Comment
     */
    'id': string;
    /**
     * 
     * @type {string}
     * @memberof Comment
     */
    'content': string;
    /**
     * 
     * @type {User}
     * @memberof Comment
     */
    'createdBy': User;
    /**
     * 
     * @type {string}
     * @memberof Comment
     */
    'createdAt': string;
}

