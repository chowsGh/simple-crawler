# web-simple-crawler

目标是支持可配置化爬虫，提供数据可视化。随缘开发。  

web-simple-crawler 爬虫是基于[webmagic](https://github.com/code4craft/webmagic)开发的网络爬虫。

web-simple-crawler-admin 前端管理界面是基于[vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)开发的。

## 功能简介

- TBD

## 设计

- TBD

## 项目结构

- TBD

## 页面预览

测试用数据是指定爬取某个猎聘搜索页面的数据

- 测试配置

  ![demo-param-json](asset\demo-param-json.png)

- 启动爬虫

  ![start](asset\start.png)

- 数据库保存的url以及数据
  
  ![start](asset\demo-url.png)
  ![start](asset\demo-data.png)
  
- 关闭爬虫

  ![shutdown](asset\shutdown.png)

## 部署安装

- TBD

### 前端

### 后端

### Q&A



## 版本规划
- v0.03 
    - 前端提供爬虫数据查询
        - 分页查询，排序，

- v0.04
    - 创建爬虫任务，修改配置
    - 爬虫配置管理
        - 起始地址配置
        - 通用匹配 转 正则配置
        - url黑名单
        - url白名单
        - url地址匹配配置
    - 爬虫数据导出
    - 弄几个网站的爬虫模板分类：例如招聘，食品，视频，图书，天气，壁纸
    - 启动关闭的  页面交互逻辑优化

- v0.05

    - url 下载失败后设置状态为错误，
    - 设置爬虫定时
    - 后台定时清洗数据
    - 数据抽取逻辑可配置化
        - 支持xpath
        - css选择
    - plugin

- v0.06  

    ...

- 未规划版本,可能丢弃

    - 脚本语言实现
    - 用户操作记录
    - 用户管理
