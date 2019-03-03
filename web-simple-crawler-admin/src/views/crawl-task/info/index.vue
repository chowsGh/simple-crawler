<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      class="task-table"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column label="名称" width="110" align="center">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="描述" align="center">
        <template slot-scope="scope">{{ scope.row.description }}</template>
      </el-table-column>
      <el-table-column label="创建者" width="200" align="center">
        <template slot-scope="scope">{{scope.row.creator}}</template>
      </el-table-column>
      <el-table-column label="状态" width="200" align="center">
        <template slot-scope="scope">{{scope.row.statusText}}</template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="taskDetail(scope.$index, scope.row)">查看参数</el-button>
          <!-- 操作按钮 -->
          <el-button
            size="mini"
            :type="scope.row.taskStatusType"
            @click="switchTaskStatus(scope.$index, scope.row)"
          >{{scope.row.taskOpertorText}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="类型" width="200" align="center">
        <template slot-scope="scope">{{scope.row.typeText}}</template>
      </el-table-column>
      <el-table-column align="center" label="创建时间">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="最后修改时间">
        <template slot-scope="scope">
          <span>{{ scope.row.lastUpdateTime }}</span>
        </template>
      </el-table-column>
    </el-table>
    <!-- 任务参数修改弹窗 -->
    <el-dialog title="任务参数" :visible.sync="dialogTaskParam">
      <el-form :model="curTaskInfo">
        <el-form-item label="活动名称" :label-width="formLabelWidth">
          <el-input v-model="curTaskInfo.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="活动区域" :label-width="formLabelWidth">
          <el-select v-model="curTaskInfo.region" placeholder="请选择活动区域">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTaskParam = false">取 消</el-button>
        <el-button type="primary" @click="dialogTaskParam = false">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 如果pageSizes 没有传则会用默认的5,10,20...的数组，调试的时候最好自己覆盖 -->
    <div class="pagination">
      <pagination
        class="demonstration"
        v-show="currentTaskPage.init"
        :pageSizes="currentTaskPage.pageSizeSelectList"
        :total="currentTaskPage.total"
        :page.sync="currentTaskPage.page"
        :limit.sync="currentTaskPage.limit"
        @pagination="getList"
      />
    </div>
  </div>
</template>
<style>
.task-table {
  height: 800px;
}
.pagination {
  display: flex;
  justify-content: center;
}
.pagination .demonstration {
  /*display：通过转为行内块配合父级元素使用text-align实现水平居中*/
  display: inline-block;
}
</style>

<script>
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination
import taskAPI from "@/api/taskAPI.js";
import { parseTime } from "@/utils";

//任务状态
let TaskStatus = {
  NOT_START: -1,
  RUNNING: 1000,
  FINISH: 10000
};
//任务相关处理逻辑封装
let TaskInfoHandle = {
  preSwitchStatus(taskInfo) {
    let switchedStatus = TaskStatus.NOT_START;
    switch (taskInfo.status) {
      case TaskStatus.NOT_START:
        switchedStatus = TaskStatus.RUNNING;
        break;
    }
    return switchedStatus;
  },
  startTask(taskInfo) {
    return taskAPI.startTask(taskInfo.id);
  },
  shutdownTask(taskInfo) {
    return taskAPI.shutdownTask(taskInfo.id);
  },
  switchStatus(taskInfo, switchingStatus) {
    switch (switchingStatus) {
      case TaskStatus.RUNNING:
        return this.startTask(taskInfo);
      case TaskStatus.NOT_START:
        return this.shutdownTask(taskInfo);
    }
  },
  switchActionText(switchingStatus) {
    switch (switchingStatus) {
      case TaskStatus.RUNNING:
        return '启动任务';
      case TaskStatus.NOT_START:
        return '停止任务';
    }
  }
};
export default {
  components: { Pagination },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: "success",
        draft: "gray",
        deleted: "danger"
      };
      return statusMap[status];
    }
  },
  data() {
    return {
      list: null,
      listLoading: true,
      dialogTaskParam: false,
      curTaskInfo: { name: "", region: "" },
      formLabelWidth: "120px",
      
      currentTaskPage: {
        page: 1,
        limit: 5,
        total: 0,
        pageSizeSelectList: [5, 10, 20]
      }
    };
  },
  mounted() {
    this.fetchData(this.currentTaskPage);
  },
  methods: {
    getList(pageInfo) {
      this.fetchData(pageInfo);
    },
    //
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`每页 ${val} 条`);
      // getList();
    },
    //显示
    showTaskParam() {
      this.dialogTaskParam = true;
    },
    //加载完成后
    afterLoad() {
      this.listLoading = false;
    },
    //修改参数
    taskDetail(index, taskInfo) {
      this.curTaskInfo = taskInfo;
      this.currentTaskPage.total = 100;
      this.showTaskParam();
    },
    //格式化显示的数据
    formatTaskInfo(taskList) {
      for (let item in taskList) {
        var taskItem = taskList[item];
        this.setTaskStatus(taskItem);
        //类型
        switch (taskItem.type) {
          case 1:
            taskItem.typeText = "HTML";
            break;
        }
        //格式时间
        taskItem.createTime = parseTime(taskItem.createTime);
        taskItem.lastUpdateTime = parseTime(taskItem.lastUpdateTime);
      }
      return taskList;
    },
    fetchData({ page, limit }, delay) {
      this.listLoading = true;
      taskAPI.getTaskInfoList({ page: page, size: limit }).then(response => {
        this.list = this.formatTaskInfo(response.result.data);
        this.currentTaskPage.total = response.result.total;
        this.currentTaskPage.page = response.result.page;
        this.currentTaskPage.limit = response.result.size;
        if (!this.currentTaskPage.init) {
          this.currentTaskPage.init = true;
        }
        this.afterLoad();
      });
    },
    setTaskStatus(taskItem, status) {
      if (status !== undefined) {
        taskItem.status = status;
      }
      //状态
      switch (taskItem.status) {
        case -1:
          taskItem.statusText = "未开始";
          taskItem.taskOpertorText = "开启爬虫";
          taskItem.taskStatusType = "success";
          break;
        case 1000:
          taskItem.statusText = "运行中";
          taskItem.taskOpertorText = "结束爬虫";
          taskItem.taskStatusType = "warning";
          break;
        default:
          taskItem.statusText = "不明状态";
      }
    },
    switchTaskStatus(index, taskInfo) {
      let switchingStatus = TaskInfoHandle.preSwitchStatus(taskInfo);
      const h = this.$createElement;
      var that = this;
      this.$msgbox({
        title: "确认",
        message: h("p", null, [
          h("span", null, "确定要 "),
          h("span", { style: "color: red;font-size:20px" }, TaskInfoHandle.switchActionText(switchingStatus)),
          h("span", null, " 吗")
        ]),
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        beforeClose: (action, instance, done) => {
          if (action === "confirm") {
            instance.confirmButtonLoading = true;
            instance.confirmButtonText = "执行中...";
            TaskInfoHandle.switchStatus(taskInfo, switchingStatus).then(
              response => {
                console.log(response);
                that.setTaskStatus(taskInfo, response.result.status);
                done();
                instance.confirmButtonLoading = false;
              },
              function(response) {
                console.error(response);
              }
            );
          } else {
            done();
          }
        }
      }).then(action => {
        this.$message({
          type: "info",
          message: "action: " + action
        });
      });
    }
  }
};
</script>
