import request from "@/utils/request";


export async function page(params) {
  return request('/zxHealth/t/lqbz/jykxxtj/pageList',{
    params
  })
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function save(params) {
  return request('/zxHealth/t/lqbz/jykxxtj/sjdwSave',
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
  return request('/zxHealth/t/lqbz/jykxxtj/sjdwUpdate',
    {
      method: 'PUT',
      data: params,
    })
}


export async function getJykxxtjList(params) {
  return request('/zxHealth/t/lqbz/jykxxtj/getJykxxtjList',{
    params
  })
}

/**
 * 导入数据
 * @param wenjianlj wenjianlj
 * @returns {Promise<any>}
 */
export async function importData(wenjianlj) {
  return request('/zxHealth/t/lqbz/jykxxtj/importData', {
    method: 'POST',
    data: {
      wenjianlj
    },
  });
}
