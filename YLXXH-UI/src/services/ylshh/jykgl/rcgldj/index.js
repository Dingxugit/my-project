import request from "@/utils/request";


export async function page(params) {
  return request('/zxHealth/t/shh/gldjb/pageList',{
    params
  })
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function save(params) {
  return request('/zxHealth/t/shh/gldjb/sjdwSave',
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
  return request('/zxHealth/t/shh/gldjb/sjdwUpdate',
    {
      method: 'PUT',
      data: params,
    })
}
