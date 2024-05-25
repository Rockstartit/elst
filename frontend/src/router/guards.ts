import { createAuthGuard, useAuth0 } from '@auth0/auth0-vue';
import { App } from 'vue';
import { availableRoutes } from 'src/router/routes';
import { authorizationParams } from 'stores/authentication/store';

export function authenticationRouteGuard(app: App) {
  return async (to) => {
    const auth0 = useAuth0();

    if (auth0.error.value) {
      return { name: availableRoutes.login_error };
    }

    return createAuthGuard({
      app,
      redirectLoginOptions: {
        authorizationParams,
      },
    })(to);
  };
}
