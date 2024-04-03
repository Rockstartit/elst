import { boot } from 'quasar/wrappers';
import { Router } from 'vue-router';
import { availableRoutes } from 'src/router/routes';
import { authenticationRouteGuard } from 'src/router/guards';
import { App } from 'vue';

export default boot(({ app, router }) => {
  const aApp = app as App;
  const aRouter = router as Router;

  const baseRoute = aRouter
    .getRoutes()
    .find((route) => route.name === availableRoutes.app_base);

  if (baseRoute) {
    baseRoute.beforeEnter = authenticationRouteGuard(app);
  }

  // expose router so auth0-vue can find it. Must be executed before the auth0-vue.
  aApp.config.globalProperties.$router = aRouter;
});
