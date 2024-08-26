export const AppConstants = {
    APPLICATION_NAME: 'assignment-app',
    APPLICATION_VERSION: '1.0.0',
    BASE_API_URL: '/api/v1',
};

export interface RouteLink {
    path: string;
    link: string;
}

export const RouterConfig = {
    HOME: {path: 'home', link: '/home', title: 'Home Page'},
    PRODUCT: {path: 'products', link: '/products', title: 'Product Page'},
    AUTH: {path: 'auth', link: '/auth', title: 'Auth Page'},
    NOT_FOUND: {path: '**', link: null, title: '404 Page'},
}