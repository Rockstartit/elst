import { LearningCyclePhase } from 'src/services/generated/openapi';

export function learningCyclePhaseLabel(phase: LearningCyclePhase) {
  switch (phase) {
    case LearningCyclePhase.Acquisition:
      return 'Erwerb';
    case LearningCyclePhase.Collaboration:
      return 'Zusammenarbeit';
    case LearningCyclePhase.Discussion:
      return 'Diskussion';
    case LearningCyclePhase.Inquiry:
      return 'Recherche';
    case LearningCyclePhase.Practice:
      return 'Übung';
    case LearningCyclePhase.Production:
      return 'Herstellung';
  }
}

export function learningCyclePhaseColor(phase: LearningCyclePhase) {
  switch (phase) {
    case LearningCyclePhase.Acquisition:
      return 'light-blue-2';
    case LearningCyclePhase.Collaboration:
      return 'amber-3';
    case LearningCyclePhase.Discussion:
      return 'blue-3';
    case LearningCyclePhase.Inquiry:
      return 'red-3';
    case LearningCyclePhase.Practice:
      return 'deep-purple-2';
    case LearningCyclePhase.Production:
      return 'light-green-3';
  }
}
