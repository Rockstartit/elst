import { useI18n } from 'vue-i18n';

export const numberRegex = /^-?[0-9]*$/;

export function useRules() {
  const { t } = useI18n();

  function isRequired(value: string) {
    return value.trim() !== '' || t('validation.field_required_error');
  }

  function isNumber(value: string) {
    if (value.toString().trim() === '') {
      return true;
    }

    if (!numberRegex.test(value)) {
      return t('validation.not_a_number');
    }

    return true;
  }

  return {
    isNumber,
    isRequired,
  };
}
