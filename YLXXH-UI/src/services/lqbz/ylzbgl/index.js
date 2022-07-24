import request from "@/utils/request";

/**
 * 分页列表
 * @param params
 * @returns {Promise<*>}
 */
export async function page(params) {
  return request("/zxHealth/t/lqbz/ylgyzbz/pageList",{
    params
  })
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function save(params) {
  return request('/zxHealth/t/lqbz/ylgyzbz/sjdwSave',
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
  return request('/zxHealth/t/lqbz/ylgyzbz/sjdwUpdate',
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
  return request("/zxHealth/t/lqbz/ylgyzbz/getDeptZbz",{
    params
  })
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function ejdwSave(params) {
  return request('/zxHealth/t/lqbz/ylgyzbz/ejdwSave',
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
  return request('/zxHealth/t/lqbz/ylgyzbz/ejdwUpdate',
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
export async function pageEjdw(params) {
  return request("/zxHealth/t/lqbz/ylgyzbz/pageEjdw",{
    params
  })
}

/**
 * 导入数据
 * @param wenjianlj wenjianlj
 * @returns {Promise<any>}
 */
export async function importData(wenjianlj) {
  return request('/zxHealth/t/lqbz/ylgyzbz/importData', {
    method: 'POST',
    data: {
      wenjianlj
    },
  });
}

/**
 * 二级单位月度报表分页列表
 * @param params
 * @returns {Promise<*>}
 */
export async function pageEjdwYdbb(params) {
  return request("/zxHealth/t/lqbz/ylgyzbz/pageEjdwYdbb",{
    params
  })
}

/**
 * 是否存在记录
 * @param params
 * @returns {Promise<*>}
 */
export async function searchExists(params) {
  return request("/zxHealth/t/lqbz/ylgyzbz/searchExists",{
    params
  })
}
