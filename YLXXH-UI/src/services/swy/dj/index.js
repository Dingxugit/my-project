import request from "@/utils/request";

/**
 * 分页列表
 * @param params
 * @returns {Promise<*>}
 */
export async function page(params) {
  return request("/zxHealth/t/swy/dj/pageList",{
    params
  })
}

/**
 * 数据导出列表
 * @param params
 * @returns {Promise<*>}
 */
export async function getDjList(params) {
  return request("/zxHealth/t/swy/dj/getDjList",{
    params
  })
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function save(params) {
  return request('/zxHealth/t/swy/dj/sjdwSave',
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
  return request('/zxHealth/t/swy/dj/sjdwUpdate',
    {
      method: 'PUT',
      data: params,
    })
}

/**
 * 导入数据
 * @param wenjianlj wenjianlj
 * @returns {Promise<any>}
 */
export async function importData(wenjianlj) {
  return request('/zxHealth/t/swy/dj/importData', {
    method: 'POST',
    data: {
      wenjianlj
    },
  });
}

