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
import { DiscussionReference } from './discussion-reference';
// May contain unused imports in some cases
// @ts-ignore
import { DiscussionState } from './discussion-state';
// May contain unused imports in some cases
// @ts-ignore
import { User } from './user';

/**
 * 
 * @export
 * @interface Discussion
 */
export interface Discussion {
    /**
     * 
     * @type {string}
     * @memberof Discussion
     */
    'id': string;
    /**
     * 
     * @type {string}
     * @memberof Discussion
     */
    'title': string;
    /**
     * 
     * @type {User}
     * @memberof Discussion
     */
    'createdBy': User;
    /**
     * 
     * @type {DiscussionState}
     * @memberof Discussion
     */
    'state': DiscussionState;
    /**
     * 
     * @type {string}
     * @memberof Discussion
     */
    'resolvedAt'?: string;
    /**
     * 
     * @type {Array<DiscussionReference>}
     * @memberof Discussion
     */
    'references': Array<DiscussionReference>;
}



