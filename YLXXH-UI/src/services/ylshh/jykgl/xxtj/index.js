import request from "@/utils/request";


export async function page(params) {
  return request('/zxHealth/t/shh/jytj/pageList',{
    params
  })
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function save(params) {
  return request('/zxHealth/t/shh/jytj/sjdwSave',
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
  return request('/zxHealth/t/shh/jytj/sjdwUpdate',
    {
      method: 'PUT',
      data: params,
    })
}


export async function getJykxxtjList(params) {
  return request('/zxHealth/t/shh/jytj/getJykxxtjList',{
    params
  })
}

/**
 * 导入数据
 * @param wenjianlj wenjianlj
 * @returns {Promise<any>}
 */
export async function importData(wenjianlj) {
  return request('/zxHealth/t/shh/jytj/importData', {
    method: 'POST',
    data: {
      wenjianlj
    },
  });
}
