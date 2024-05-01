import { Notify, QNotifyCreateOptions } from 'quasar';
import { useI18n } from 'vue-i18n';
import { AxiosError } from 'axios';

export interface NotificationOptions {
  message?: string;
  type?: string;
  timeout?: number;
}

interface ApiError {
  error: string;
  message: string;
  status: string;
  timestamp: string;
}

export function useNotifications() {
  const { t, te } = useI18n();

  function base(args: NotificationOptions & QNotifyCreateOptions) {
    let messageKey =
      'notifications.' +
      (args.message?.toLowerCase().replace(' ', '_') ?? 'unknown');

    if (!te(messageKey) && messageKey.includes('invalid_attribute')) {
      messageKey = 'notification_invalid_attribute';
    }

    Notify.create({
      ...args,
      position: 'bottom-right',
      timeout: 700,
      message: t(messageKey),
    });
  }

  function positive(args?: NotificationOptions) {
    base({
      ...args,
      type: 'positive',
    });
  }

  function negative(args?: NotificationOptions) {
    base({
      ...args,
      type: 'negative',
    });
  }

  function warning(args?: NotificationOptions) {
    base({
      ...args,
      type: 'warning',
    });
  }

  function info(args?: NotificationOptions) {
    base({
      ...args,
      type: 'info',
      textColor: 'indigo-10',
    });
  }

  function saved() {
    positive({
      message: 'saved',
    });
  }

  function added() {
    positive({
      message: 'added',
    });
  }

  function created() {
    positive({
      message: 'created',
    });
  }

  function success(message?: string) {
    positive({
      message: message ?? 'success',
    });
  }

  function deleted() {
    positive({
      message: 'deleted',
    });
  }

  function notAuthorized() {
    negative({
      message: 'not_authorized',
    });
  }

  function apiError(err: AxiosError) {
    if (err.code === 'ERR_NETWORK') {
      negative({
        message: 'network_error',
      });
    } else if (err.code === 'ERR_BAD_REQUEST') {
      const error: ApiError = err.response?.data as ApiError;

      if (!error || (err.response?.data as { path: string }).path) {
        negative({
          message: 'missing_error',
        });

        return;
      }

      negative({
        message: error.error,
      });
    } else {
      negative({
        message: 'missing_error',
      });
    }
  }

  return {
    info,
    saved,
    added,
    created,
    deleted,
    warning,
    success,
    apiError,
    negative,
    notAuthorized,
  };
}
