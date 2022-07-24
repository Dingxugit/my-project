// @ts-nocheck
import { Component } from 'react';
import { ApplyPluginsType } from 'umi';
import dva from 'dva';
// @ts-ignore
import createLoading from 'F:/workSpace/my-project/YLXXH-UI/node_modules/_dva-loading@3.0.22@dva-loading/dist/index.esm.js';
import { plugin, history } from '../core/umiExports';
import ModelGlobal0 from 'F:/workSpace/my-project/YLXXH-UI/src/models/global.js';
import ModelSetting1 from 'F:/workSpace/my-project/YLXXH-UI/src/models/setting.js';
import ModelUser2 from 'F:/workSpace/my-project/YLXXH-UI/src/models/user.js';
import ModelModel3 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/account/center/model.js';
import ModelModel4 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/account/settings/model.js';
import ModelModel5 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/dashboard/analysis/model.jsx';
import ModelModel6 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/dashboard/monitor/model.js';
import ModelModel7 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/dashboard/workplace/model.js';
import ModelModel8 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/form/advanced-form/model.js';
import ModelModel9 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/form/basic-form/model.js';
import ModelModel10 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/form/step-form/model.js';
import ModelModel11 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/list/basic-list/model.js';
import ModelModel12 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/list/card-list/model.js';
import ModelModel13 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/list/search/applications/model.js';
import ModelModel14 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/list/search/articles/model.js';
import ModelModel15 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/list/search/projects/model.js';
import ModelModel16 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/profile/advanced/model.js';
import ModelModel17 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/example/profile/basic/model.js';
import ModelModel18 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/sys/button/model.js';
import ModelModel19 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/sys/dept/model.js';
import ModelModel20 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/sys/home/model.js';
import ModelModel21 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/sys/menu/model.js';
import ModelModel22 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/sys/role/model.js';
import ModelModel23 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/sys/system/model.js';
import ModelModel24 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/sys/user/model.js';
import ModelModel25 from 'F:/workSpace/my-project/YLXXH-UI/src/pages/user/login/model.js';

let app:any = null;

export function _onCreate(options = {}) {
  const runtimeDva = plugin.applyPlugins({
    key: 'dva',
    type: ApplyPluginsType.modify,
    initialValue: {},
  });
  app = dva({
    history,
    
    ...(runtimeDva.config || {}),
    // @ts-ignore
    ...(typeof window !== 'undefined' && window.g_useSSR ? { initialState: window.g_initialProps } : {}),
    ...(options || {}),
  });
  
  app.use(createLoading());
  (runtimeDva.plugins || []).forEach((plugin:any) => {
    app.use(plugin);
  });
  app.model({ namespace: 'global', ...ModelGlobal0 });
app.model({ namespace: 'setting', ...ModelSetting1 });
app.model({ namespace: 'user', ...ModelUser2 });
app.model({ namespace: 'model', ...ModelModel3 });
app.model({ namespace: 'model', ...ModelModel4 });
app.model({ namespace: 'model', ...ModelModel5 });
app.model({ namespace: 'model', ...ModelModel6 });
app.model({ namespace: 'model', ...ModelModel7 });
app.model({ namespace: 'model', ...ModelModel8 });
app.model({ namespace: 'model', ...ModelModel9 });
app.model({ namespace: 'model', ...ModelModel10 });
app.model({ namespace: 'model', ...ModelModel11 });
app.model({ namespace: 'model', ...ModelModel12 });
app.model({ namespace: 'model', ...ModelModel13 });
app.model({ namespace: 'model', ...ModelModel14 });
app.model({ namespace: 'model', ...ModelModel15 });
app.model({ namespace: 'model', ...ModelModel16 });
app.model({ namespace: 'model', ...ModelModel17 });
app.model({ namespace: 'model', ...ModelModel18 });
app.model({ namespace: 'model', ...ModelModel19 });
app.model({ namespace: 'model', ...ModelModel20 });
app.model({ namespace: 'model', ...ModelModel21 });
app.model({ namespace: 'model', ...ModelModel22 });
app.model({ namespace: 'model', ...ModelModel23 });
app.model({ namespace: 'model', ...ModelModel24 });
app.model({ namespace: 'model', ...ModelModel25 });
  return app;
}

export function getApp() {
  return app;
}

/**
 * whether browser env
 * 
 * @returns boolean
 */
function isBrowser(): boolean {
  return typeof window !== 'undefined' &&
  typeof window.document !== 'undefined' &&
  typeof window.document.createElement !== 'undefined'
}

export class _DvaContainer extends Component {
  constructor(props: any) {
    super(props);
    // run only in client, avoid override server _onCreate()
    if (isBrowser()) {
      _onCreate()
    }
  }

  componentWillUnmount() {
    let app = getApp();
    app._models.forEach((model:any) => {
      app.unmodel(model.namespace);
    });
    app._models = [];
    try {
      // 释放 app，for gc
      // immer 场景 app 是 read-only 的，这里 try catch 一下
      app = null;
    } catch(e) {
      console.error(e);
    }
  }

  render() {
    let app = getApp();
    app.router(() => this.props.children);
    return app.start()();
  }
}
