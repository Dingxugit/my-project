import request from "@/utils/request";

/**
 * 分页列表
 * @param params
 * @returns {Promise<*>}
 */
export async function page(params) {
  return request("/zxHealth/t/swy/swylz/pageList",{
    params
  })
}

/**
 * 数据导出列表
 * @param params
 * @returns {Promise<*>}
 */
export async function getDeptZbzList(params) {
  return request("/zxHealth/t/swy/swylz/getDeptZbzList",{
    params
  })
}
/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function save(params) {
  return request('/zxHealth/t/swy/swylz/sjdwSave',
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
  return request('/zxHealth/t/swy/swylz/sjdwUpdate',
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
  return request('/zxHealth/t/swy/swylz/importData', {
    method: 'POST',
    data: {
      wenjianlj
    },
  });
}
/**
 * 是否存在记录
 * @param params
 * @returns {Promise<*>}
 */
export async function searchExists(params) {
  return request("/zxHealth/t/swy/swylz/searchExists",{
    params
  })
}
