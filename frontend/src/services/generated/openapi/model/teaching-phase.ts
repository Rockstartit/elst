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
import { LearningCyclePhase } from './learning-cycle-phase';
// May contain unused imports in some cases
// @ts-ignore
import { LearningMaterial } from './learning-material';
// May contain unused imports in some cases
// @ts-ignore
import { TeacherPresence } from './teacher-presence';

/**
 * 
 * @export
 * @interface TeachingPhase
 */
export interface TeachingPhase {
    /**
     * 
     * @type {string}
     * @memberof TeachingPhase
     */
    'id': string;
    /**
     * 
     * @type {string}
     * @memberof TeachingPhase
     */
    'topic': string;
    /**
     * 
     * @type {number}
     * @memberof TeachingPhase
     */
    'timeFrame'?: number;
    /**
     * 
     * @type {LearningCyclePhase}
     * @memberof TeachingPhase
     */
    'phase'?: LearningCyclePhase;
    /**
     * 
     * @type {Array<LearningMaterial>}
     * @memberof TeachingPhase
     */
    'learningMaterials': Array<LearningMaterial>;
    /**
     * 
     * @type {TeacherPresence}
     * @memberof TeachingPhase
     */
    'teacherPresence'?: TeacherPresence;
    /**
     * 
     * @type {number}
     * @memberof TeachingPhase
     */
    'order': number;
}



