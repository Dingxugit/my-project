import request from '@/utils/request';

/**
 * 统计用户数量
 * @returns {Promise<any>}
 */
export async function user() {
  return request('/zxHealth/sys/count/user');
}

/**
 * 每个角色中有多少用户
 * @returns {Promise<any>}
 */
export async function roleUser() {
  return request('/zxHealth/sys/count/roleUser');
}

/**
 * 部门用户统计
 * @returns {Promise<any>}
 */
export async function deptUser() {
  return request('/zxHealth/sys/count/deptUser');
}
