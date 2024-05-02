import { QDialogOptions } from 'quasar';
import CreateOrEditTeachingPhaseDialog from 'src/lessons/view-teaching-unit/CreateOrEditTeachingPhaseDialog.vue';
import {
  LearningCyclePhase,
  TeachingPhase,
} from 'src/services/generated/openapi';

export interface CreateOrEditTeachingPhaseDialogProps {
  teachingPhase?: TeachingPhase;
}

export interface CreateOrEditTeachingPhaseDialogResult {
  topic: string;
  timeFrame?: number;
  phase?: LearningCyclePhase;
}

export function createTeachingPhaseDialog(): QDialogOptions {
  return {
    component: CreateOrEditTeachingPhaseDialog,
  };
}

export function editTeachingPhaseDialog(
  dialogProps: CreateOrEditTeachingPhaseDialogProps
): QDialogOptions {
  return {
    component: CreateOrEditTeachingPhaseDialog,
    componentProps: dialogProps,
  };
}
