import request from "@/utils/request";

export async function save(params) {
  return request('/zxHealth/t/car/save',
  {
    method: 'POST',
    data: params,
  })
}

export async function update(params) {
  return request('/zxHealth/t/car/update',
    {
      method: 'PUT',
      data: params,
    })
}

export async function page(params) {
  return request('/zxHealth/t/car/page',{
    params
  })
}

export async function getCarList(params) {
  return request('/zxHealth/t/car/getCarList',{
    params
  })
}

/**
 * 导入数据
 * @param wenjianlj wenjianlj
 * @returns {Promise<any>}
 */
export async function importData(wenjianlj) {
  return request('/zxHealth/t/car/importData', {
    method: 'POST',
    data: {
      wenjianlj
    },
  });
}

export async function getCarInfoBynum(params) {
  return request('/zxHealth/t/car/getCarInfoBynum',{
    params
  })
}
