import { TeacherPresence } from 'src/services/generated/openapi';

export function useTeacherPresence() {
  function getTeacherPresenceLabel(
    teacherPresence: TeacherPresence | undefined
  ) {
    switch (teacherPresence) {
      case TeacherPresence.Absent:
        return 'Nicht benötigt';
      case TeacherPresence.OnSite:
        return 'Verfügbar';
      case TeacherPresence.Remote:
        return 'Online';
    }

    return 'Keine Angabe';
  }

  return {
    getTeacherPresenceLabel,
  };
}
