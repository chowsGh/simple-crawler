import request from '@/utils/request'
import APIConstants from './apiConstants.js'

export default {
  getTaskInfoList(params) {
    return request({
      url: APIConstants.taskInfoList,
      method: 'get',
      params
    })
  },
  //启动爬虫
  startTask(id) {
    return request.post( APIConstants.startTask, 
      { id }
    );
  },
  shutdownTask(id) {
    return request.post( APIConstants.shutdownTask, 
      { id }
    );
  }

}
