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



/**
 * 
 * @export
 * @enum {string}
 */

export const RequirementType = {
    Learner: 'LEARNER',
    Teacher: 'TEACHER',
    Pedagogical: 'PEDAGOGICAL',
    Technological: 'TECHNOLOGICAL',
    Institutional: 'INSTITUTIONAL'
} as const;

export type RequirementType = typeof RequirementType[keyof typeof RequirementType];



