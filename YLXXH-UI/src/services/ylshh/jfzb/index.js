import request from "@/utils/request";

/**
 * 分页列表
 * @param params
 * @returns {Promise<*>}
 */
export async function page(params) {
  return request("/zxHealth/t/shh/yljfzbz/pageList",{
    params
  })
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function save(params) {
  return request('/zxHealth/t/shh/yljfzbz/sjdwSave',
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
  return request('/zxHealth/t/shh/yljfzbz/sjdwUpdate',
    {
      method: 'PUT',
      data: params,
    })
}

/**
 * 分页列表
 * @param params
 * @returns {Promise<*>}
 */
export async function getDeptZbz(params) {
  return request("/zxHealth/t/shh/yljfzbz/getDeptZbz",{
    params
  })
}

/**
 * 分页列表
 * @param params
 * @returns {Promise<*>}
 */
export async function pageEjdw(params) {
  return request("/zxHealth/t/shh/yljfzbz/pageEjdw",{
    params
  })
}

/**
 * 导入数据
 * @param wenjianlj wenjianlj
 * @returns {Promise<any>}
 */
export async function importData(wenjianlj) {
  return request('/zxHealth/t/shh/yljfzbz/importData', {
    method: 'POST',
    data: {
      wenjianlj
    },
  });
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function ejdwSave(params) {
  return request('/zxHealth/t/shh/yljfzbz/ejdwSave',
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
export async function ejdwUpdate(params) {
  return request('/zxHealth/t/shh/yljfzbz/ejdwUpdate',
    {
      method: 'PUT',
      data: params,
    })
}

/**
 * 二级单位月度报表分页列表
 * @param params
 * @returns {Promise<*>}
 */
export async function pageEjdwYdbb(params) {
  return request("/zxHealth/t/shh/yljfzbz/pageEjdwYdbb",{
    params
  })
}

/**
 * 是否存在记录
 * @param params
 * @returns {Promise<*>}
 */
export async function searchExists(params) {
  return request("/zxHealth/t/shh/yljfzbz/searchExists",{
    params
  })
}
