import request from '@/utils/request';

export async function accountLogin(params) {
  return request('/zxHealth/oauth/token', {
    method: 'POST',
    params: {
      grant_type: 'password',
      scope: 'all',
      client_id: 'jmonkey_client_id',
      client_secret: 'jmonkey_client_secret',
      username: params.userName,
      password: params.password
    },
  });
}
export async function getFakeCaptcha(mobile) {
  return request(`/api/login/captcha?mobile=${mobile}`);
}
