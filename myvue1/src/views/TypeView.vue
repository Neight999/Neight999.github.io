<template>
  <div>
    <div style="margin-bottom: 12px;">
      <el-input style="width: 200px;" placeholder="请输入分类名称" v-model="params.name" clearable>
      </el-input>
      <el-button style="margin-left: 10px;" type="warning" @click="findBySearch()">查询</el-button>
      <el-button style="margin-left: 10px;" type="primary" @click="reset()">清空</el-button>
      <el-button style="margin-left: 10px;" type="primary" @click="add()" v-if="user.role === 'ROLE_ADMIN'">新增</el-button>
      <el-popconfirm title="确定删除这些数据吗？" @confirm="delBatch()" v-if="user.role === 'ROLE_ADMIN'">
        <el-button slot="reference" type="danger" style="margin-left: 5px;">批量删除</el-button>
      </el-popconfirm>
      <el-button style="margin-left: 10px;" type="success" @click="exp()" v-if="user.role === 'ROLE_ADMIN'">导出报表</el-button>
      <el-upload action="http://localhost:8080/api/type/upload" :show-file-list="false" style="display: 
      inline-block; margin-left: 10px;" v-if="user.role === 'ROLE_ADMIN'"
       :on-success="successUpload">
        <el-button size="small" type="primary">批量导入</el-button>
      </el-upload>
    </div>
    <el-table :data="tableData" stripe style="width: 100%" ref="table" 
    @selection-change="handleSelectionChange" :row-key="getRowKeys" >
      <el-table-column type="selection" width="55" :reserve-selection="true" v-if="user.role === 'ROLE_ADMIN'">
      </el-table-column>
      <el-table-column prop="name" label="分类名称">
      </el-table-column>
      <el-table-column prop="description" label="分类描述">
      </el-table-column>
      <el-table-column label="操作" v-if="user.role === 'ROLE_ADMIN'">
        <template slot-scope="scope">
          <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
            <el-button slot="reference" type="danger" style="margin-left: 5px;">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 15px;">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="params.pageNum"
        :page-sizes="[5, 10, 15, 20]" :page-size="params.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="35%">
        <el-form :model="form">
          <el-form-item label="分类名称" label-width="20%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="分类描述" label-width="20%">
            <el-input v-model="form.description" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';
export default {
  data() {
    return {
      params: {
        name: '',
        pageNum: 1,
        pageSize: 5
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      form: {
      },
      multipleSelection: [],
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {}
    }
  },
  //页面加载的时候，做一些事情，在create里面
  created() {
    this.findBySearch();
  },
  //定义一些页面上控件触发的事件调用的方法
  methods: {
    findBySearch() {
      request.get('/type/search', {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    },
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    edit(obj) {
      this.form = obj;
      this.dialogFormVisible = true;
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: '',
      }
      this.findBySearch();
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    submit() {
      request.post('/type', this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          // this.$message.error('操作失败');
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      });
    },
    del(id) {
      request.delete('/type/' + id).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      });
    },
    delBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning('请选择要删除的数据');
        return;
      }
      request.put("/type/delBatch", this.multipleSelection).then(res => {
        if (res.code === '0') {
          this.$message.success('批量删除成功');
          this.findBySearch();
        } else {
          this.$message.success(res.msg);
        }
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getRowKeys(row) {
      return row.id;
    },
    exp() {
      let user = localStorage.getItem('user');
      location.href = 'http://localhost:8080/api/type/export?token=' + JSON.parse(user).token;
    },
    successUpload(res){
      if(res.code ==='0'){
        this.$message.success('批量导入成功')
        this.findBySearch();
      }else{
        this.$message.error(res.msg)
      }
    }
  },

}
</script>
