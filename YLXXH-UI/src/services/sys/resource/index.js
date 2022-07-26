import request from '@/utils/request';

export async function list(type) {
  return request(`/zxHealth/sys/resource/list`, {
    method: 'GET',
    params: {
      type
    }
  });
}

/**
 * 获取前端路由信息
 * @returns {Promise<any>}
 */
export async function routeList() {
  return request(`/zxHealth/sys/resource/routeList`);
}

/**
 * 获取当前登录人的资源信息
 * @returns {Promise<any>}
 */
export async function loginResource() {
  return request(`/zxHealth/sys/resource/loginResource`);
}
