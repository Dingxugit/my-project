import request from "@/utils/request";

export async function getCarInfo(params) {
  return request('/zxHealth/t/car/getCarInfo',{
    params
  })
}

export async function getSysDeptList(params) {
  return request('/zxHealth/sys/dept/getSysDeptList',{
    params
  })
}
