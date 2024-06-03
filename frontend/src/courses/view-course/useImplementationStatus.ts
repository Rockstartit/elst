import { ImplementationStatus } from 'src/services/generated/openapi';
import { ref } from 'vue';

export function useImplementationStatus() {
  const implementationStatusList = ref([
    ImplementationStatus.NotStarted,
    ImplementationStatus.Started,
    ImplementationStatus.WaitingForFeedback,
    ImplementationStatus.Completed,
  ]);

  function getImplementationStatusIcon(status: ImplementationStatus) {
    switch (status) {
      case ImplementationStatus.NotStarted:
        return 'mdi-circle-outline';
      case ImplementationStatus.Started:
        return 'mdi-pause-circle-outline';
      case ImplementationStatus.WaitingForFeedback:
        return 'mdi-alert-circle-outline';
      case ImplementationStatus.Completed:
        return 'mdi-check-circle-outline';
    }
  }

  function getImplementationStatusColor(status: ImplementationStatus) {
    switch (status) {
      case ImplementationStatus.NotStarted:
        return 'black';
      case ImplementationStatus.Started:
        return 'primary';
      case ImplementationStatus.WaitingForFeedback:
        return 'amber-10';
      case ImplementationStatus.Completed:
        return 'green';
    }
  }

  function getImplementationStatusLabel(status: ImplementationStatus) {
    switch (status) {
      case ImplementationStatus.NotStarted:
        return 'Nicht begonnen';
      case ImplementationStatus.Started:
        return 'Begonnen';
      case ImplementationStatus.WaitingForFeedback:
        return 'Warte auf Feedback';
      case ImplementationStatus.Completed:
        return 'Abgeschlossen';
    }
  }

  return {
    implementationStatusList,
    getImplementationStatusIcon,
    getImplementationStatusLabel,
    getImplementationStatusColor,
  };
}
