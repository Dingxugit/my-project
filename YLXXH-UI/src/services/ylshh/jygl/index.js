import request from "@/utils/request";

/**
 * 分页获取列表
 * @param params
 * @returns {Promise<*>}
 */
export async function page(params) {
  return request('/zxHealth/t/shh/jypz/pageList',{
    params
  })
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function save(params) {
  return request('/zxHealth/t/shh/jypz/sjdwSave',
    {
      method: 'POST',
      data: params,
    })
}

/**
 * 修改
 * @param params
 * @returns {Promise<*>}
 */
export async function update(params) {
  return request('/zxHealth/t/shh/jypz/sjdwUpdate',
    {
      method: 'PUT',
      data: params,
    })
}

/**
 * 获取上一次公里数
 * @param params
 * @returns {Promise<*>}
 */
export async function getLastCzpzBycarnum(params) {
  return request('/zxHealth/t/shh/jypz/getLastCzpzBycarnum',{
    params
  })
}

/**
 * 导出凭证列表
 * @param params
 * @returns {Promise<*>}
 */
export async function getCzpz(params) {
  return request('/zxHealth/t/shh/jypz/getCzpz',{
    params
  })
}

/**
 * 导入数据
 * @param wenjianlj wenjianlj
 * @returns {Promise<any>}
 */
export async function importData(wenjianlj) {
  return request('/zxHealth/t/shh/jypz/importData', {
    method: 'POST',
    data: {
      wenjianlj
    },
  });
}

/**
 * 获取车辆加油凭证信息
 * @param params
 * @returns {Promise<*>}
 */
export async function getJypzById(params) {
  return request('/zxHealth/t/shh/jypz/getJypzById',{
    params
  })
}

/**
 * 将该凭证保存到指标账
 * @param params
 * @returns {Promise<*>}
 */
export async function saveTozbz(params) {
  return request('/zxHealth/t/shh/jypz/saveTozbz',{
    params
  })
}
