// @ts-nocheck
import React from 'react';
import { ApplyPluginsType, dynamic } from 'F:/workSpace/my-project/YLXXH-UI/node_modules/_@umijs_runtime@3.5.30@@umijs/runtime';
import * as umiExports from './umiExports';
import { plugin } from './plugin';
import LoadingComponent from '@/components/layout/PageLoading/index';

export function getRoutes() {
  const routes = [
  {
    "path": "/",
    "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__BlankLayout' */'F:/workSpace/my-project/YLXXH-UI/src/layouts/BlankLayout'), loading: LoadingComponent}),
    "routes": [
      {
        "path": "/user",
        "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__UserLayout' */'F:/workSpace/my-project/YLXXH-UI/src/layouts/UserLayout'), loading: LoadingComponent}),
        "routes": [
          {
            "path": "/user",
            "redirect": "/user/login",
            "exact": true
          },
          {
            "name": "login",
            "icon": "smile",
            "path": "/user/login",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__user__login' */'F:/workSpace/my-project/YLXXH-UI/src/pages/user/login'), loading: LoadingComponent}),
            "exact": true
          },
          {
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__error__404' */'F:/workSpace/my-project/YLXXH-UI/src/pages/error/404'), loading: LoadingComponent}),
            "exact": true
          }
        ]
      },
      {
        "path": "/guide",
        "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__GuideLayout' */'F:/workSpace/my-project/YLXXH-UI/src/layouts/GuideLayout'), loading: LoadingComponent}),
        "exact": true
      },
      {
        "path": "/",
        "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__BasicLayout' */'F:/workSpace/my-project/YLXXH-UI/src/layouts/BasicLayout'), loading: LoadingComponent}),
        "routes": [
          {
            "path": "/",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__RedirectLayout' */'F:/workSpace/my-project/YLXXH-UI/src/layouts/RedirectLayout'), loading: LoadingComponent}),
            "exact": true
          },
          {
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__error__404' */'F:/workSpace/my-project/YLXXH-UI/src/pages/error/404'), loading: LoadingComponent}),
            "exact": true
          }
        ]
      }
    ]
  }
];

  // allow user to extend routes
  plugin.applyPlugins({
    key: 'patchRoutes',
    type: ApplyPluginsType.event,
    args: { routes },
  });

  return routes;
}
