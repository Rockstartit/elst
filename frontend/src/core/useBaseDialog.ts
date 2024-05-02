import { QBtnProps, QDialogOptions } from 'quasar';

export function stringPromptDialog(
  title: string,
  label: string,
  options?: {
    ok?: QBtnProps;
    isValid?: (value: string) => boolean;
    model?: string;
    message?: string;
  }
): QDialogOptions {
  const okProps = options?.ok ? options.ok : {};

  return {
    title: title,
    message: options?.message,
    ok: {
      noCaps: true,
      unelevated: true,
      style: {
        width: '150px',
      },
      ...okProps,
    } as QBtnProps,
    prompt: {
      model: options?.model ?? '',
      isValid: options?.isValid,
      label: label,
      outlined: true,
      hideBottomSpace: true,
      borderless: true,
      stackLabel: true,
      dense: true,
      lazyRules: true,
      bgColor: 'grey-3',
      labelColor: 'grey-10',
    },
  };
}

export function confirmDialog(): QDialogOptions {
  return {
    title: 'Aktion bestätigen',
    message: 'Soll die Aktion durchgeführt werden?',
    ok: {
      noCaps: true,
      label: 'Bestätigen',
      style: {
        width: '150px',
      },
      unelevated: true,
    } as QBtnProps,
    cancel: {
      noCaps: true,
      flat: true,
      unelevated: true,
    } as QBtnProps,
  };
}

export function isRequired(value: string) {
  return value.length > 0;
}
