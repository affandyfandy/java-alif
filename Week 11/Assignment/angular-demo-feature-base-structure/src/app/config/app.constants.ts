export const AppConstants = {
  APPLICATION_NAME: 'Javascript Application Framework',
  BASE_API_URL: '/my-api',
  LOG_OFF_ICON: 'sign-out'
};

export interface RouteLink {
  path: string;
  link: string;
}

export const RouterConfig = {
  HOME: {path: '', link: '/'},
  CUSTOMER: {path: 'customer', link: '/customer', title: 'Customer Page'},
  DEMO: {path: 'demo', link: '/demo', title: 'Directive/Pipe demo'},
  NOT_FOUND: {path: '**', link: null, title: 'Page Not Found'}
};
