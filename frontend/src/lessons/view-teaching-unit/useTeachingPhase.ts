import { LearningCyclePhase } from 'src/services/generated/openapi';

export function learningCyclePhaseLabel(phase: LearningCyclePhase) {
  switch (phase) {
    case LearningCyclePhase.Conceptualization:
      return 'Konzeptionalisierung';
    case LearningCyclePhase.Construction:
      return 'Konstruktion';
    case LearningCyclePhase.Dialog:
      return 'Dialog';
  }
}

export function learningCyclePhaseColor(phase: LearningCyclePhase) {
  switch (phase) {
    case LearningCyclePhase.Conceptualization:
      return 'red-3';
    case LearningCyclePhase.Construction:
      return 'amber-3';
    case LearningCyclePhase.Dialog:
      return 'green-3';
  }
}
