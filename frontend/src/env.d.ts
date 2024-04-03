/* eslint-disable */

declare namespace NodeJS {
  interface ProcessEnv {
    NODE_ENV: string;
    VUE_ROUTER_MODE: 'hash' | 'history' | 'abstract' | undefined;
    VUE_ROUTER_BASE: string | undefined;
    AUTH0_DOMAIN: string;
    AUTH0_CLIENT_ID: string;
    AUTH0_AUDIENCE: string;
  }
}
