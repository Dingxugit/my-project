import request from '@/utils/request';

export async function page(params) {
  return request('/zxHealth/log/handle/page', {
    params,
  });
}
