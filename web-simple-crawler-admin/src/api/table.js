import request from '@/utils/request'

export function getList(params) {
  return request({
    url: 'http://127.0.0.1:18080/simple-web-crawler/task/crawl-data',
    method: 'get',
    params
  })
}
