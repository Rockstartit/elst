import { boot } from 'quasar/wrappers';
import { createAuth0 } from '@auth0/auth0-vue';

export default boot(({ app }) => {
  if (!process.env.AUTH0_DOMAIN || !process.env.AUTH0_CLIENT_ID) {
    throw Error('authentication provider not configured');
  }

  app.use(
    createAuth0({
      domain: process.env.AUTH0_DOMAIN,
      clientId: process.env.AUTH0_CLIENT_ID,
      authorizationParams: {
        audience: process.env.AUTH0_AUDIENCE,
      },
      cacheLocation: 'localstorage',
      useRefreshTokens: true,
    })
  );
});
