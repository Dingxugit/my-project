import request from '@/utils/request';

export async function page(params) {
  return request('/zxHealth/sys/task/page', {
    params,
  });
}

/**
 * 校验任务名称是否可用
 *  1、与其他任务的名称不重复
 * @param params params
 * @returns {Promise<any>}
 */
export async function checkName(params) {
  return request('/zxHealth/sys/task/checkName', {
    method: 'POST',
    data: params,
  });
}

/**
 * 验证定时任务规则表达式
 * @param rule 规则表达式
 * @returns {Promise<any>}
 */
export async function checkRule(rule) {
  return request('/zxHealth/sys/task/checkRule', {
    method: 'GET',
    params: { rule },
  });
}

/**
 * 校验任务类是否可用
 * @param params params
 * @returns {Promise<any>}
 */
export async function checkClassName(params) {
  return request('/zxHealth/sys/task/checkClassName', {
    method: 'POST',
    data: params,
  });
}

/**
 * 立即执行一次任务
 * @param params params
 * @returns {Promise<any>}
 */
export async function startNow(params) {
  return request('/zxHealth/sys/task/startNow', {
    method: 'POST',
    data: params,
  });
}

/**
 * 恢复任务
 * @param params params
 * @returns {Promise<any>}
 */
export async function resume(params) {
  return request('/zxHealth/sys/task/resume', {
    method: 'POST',
    data: params,
  });
}

/**
 * 暂停任务
 * @param params params
 * @returns {Promise<any>}
 */
export async function pause(params) {
  return request('/zxHealth/sys/task/pause', {
    method: 'POST',
    data: params,
  });
}

/**
 * 删除任务
 * @param params params
 * @returns {Promise<any>}
 */
export async function remove(params) {
  return request('/zxHealth/sys/task/remove', {
    method: 'POST',
    data: params,
  });
}

/**
 * 保存任务信息
 * @param params params
 * @returns {Promise<any>}
 */
export async function save(params) {
  return request('/zxHealth/sys/task/save', {
    method: 'POST',
    data: params,
  });
}

/**
 * 修改任务信息
 * @param params params
 * @returns {Promise<any>}
 */
export async function update(params) {
  return request('/zxHealth/sys/task/update', {
    method: 'PUT',
    data: params,
  });
}
