import request from "@/utils/request";


export async function page(params) {
  return request('/zxHealth/t/swy/dcyh/pageList',{
    params
  })
}

/**
 * 保存
 * @param params
 * @returns {Promise<*>}
 */
export async function save(params) {
  return request('/zxHealth/t/swy/dcyh/sjdwSave',
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
  return request('/zxHealth/t/swy/dcyh/sjdwUpdate',
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
export async function getLastGlsBycarnum(params) {
  return request('/zxHealth/t/swy/dcyh/getLastGlsBycarnum',{
    params
  })
}

/**
 * 获取单车油耗列表
 * @param params
 * @returns {Promise<*>}
 */
export async function getDcyhList(params) {
  return request('/zxHealth/t/swy/dcyh/getDcyhList',{
    params
  })
}

/**
 * 导入数据
 * @param wenjianlj wenjianlj
 * @returns {Promise<any>}
 */
export async function importData(wenjianlj) {
  return request('/zxHealth/t/swy/dcyh/importData', {
    method: 'POST',
    data: {
      wenjianlj
    },
  });
}

/**
 * 获取单车油耗信息
 * @param params
 * @returns {Promise<*>}
 */
export async function getDcyhxxBycarnum(params) {
  return request('/zxHealth/t/swy/dcyh/getDcyhxxBycarnum',{
    params
  })
}

/**
 * 获取单车油耗分析
 * @param params
 * @returns {Promise<*>}
 */
export async function pageEjdwYhfx(params) {
  return request('/zxHealth/t/swy/dcyh/pageEjdwYhfx',{
    params
  })
}
