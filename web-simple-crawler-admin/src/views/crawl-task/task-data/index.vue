<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      class="task-table"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row>
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="任务id" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.taskId }}
        </template>
      </el-table-column>
      <el-table-column label="data" align="center">
        <template slot-scope="scope">
          {{ scope.row.data }}
        </template>
      </el-table-column>
      <el-table-column label="url" width="200" align="center">
        <template slot-scope="scope">
          <a :href="scope.row.url" target="_blank">{{scope.row.url}}</a>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="create time" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<style>
.task-table {
  height: 100%
}
</style>

<script>
import taskAPI from '@/api/taskAPI.js'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      listLoading: true
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      taskAPI.getTaskInfoList({page:1,size:5}).then(response => {
        console.log(response);
      })
    }
  }
}
</script>
