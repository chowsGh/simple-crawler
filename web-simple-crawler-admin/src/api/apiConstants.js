let baseUrl = "http://127.0.0.1:18080/simple-web-crawler/";
let taskData = baseUrl + "task/";
let taskInfo = baseUrl + "taskinfo/";
let taskManagement = baseUrl + "taskManagement/";
export default {
    //taskData 可以分页获取任务数据
    taskDataList: taskData + 'crawl-data',
    //taskinfo
    taskInfoList: taskInfo + 'list',
    //operate crawler
    startTask:  taskManagement + 'start',
    shutdownTask:  taskManagement + 'shutdown'
}