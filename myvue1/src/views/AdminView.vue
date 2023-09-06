<template>
  <div>
    <div style="margin-bottom: 12px;">
      <el-input style="width: 200px;" placeholder="请输入姓名" v-model="params.name" clearable>
      </el-input>
      <el-input style="width: 200px; margin-left: 5px;" placeholder="请输入手机号" v-model="params.phone" clearable>
      </el-input>
      <el-button style="margin-left: 10px;" type="warning" @click="findBySearch()">查询</el-button>
      <el-button style="margin-left: 10px;" type="primary" @click="reset()">清空</el-button>
      <el-button style="margin-left: 10px;" type="primary" @click="add()">新增</el-button>
    </div>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="img" label="头像">
        <template v-slot="scope">
          <el-image style="width: 65px; height: 65px; border-radius: 50%;"
            :src="'http://localhost:8080/api/files/' + scope.row.img"
            :preview-src-list="['http://localhost:8080/api/files/' + scope.row.img]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="姓名">
      </el-table-column>
      <el-table-column prop="gender" label="性别">
      </el-table-column>
      <el-table-column prop="age" label="年龄">
      </el-table-column>
      <el-table-column prop="phone" label="电话">
      </el-table-column>
      <el-table-column prop="role" label="角色">
      </el-table-column>
      <el-table-column label="操作">
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
          <el-form-item label="姓名" label-width="20%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="性别" label-width="20%">
            <el-radio v-model="form.gender" label="男">男</el-radio>
            <el-radio v-model="form.gender" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="年龄" label-width="20%">
            <el-input v-model="form.age" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="头像" label-width="20%">
            <el-upload action="http://localhost:8080/api/files/upload" :on-success="successUpload">
              <el-button size="small" type="primary">上传头像</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="电话" label-width="20%">
            <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="角色" label-width="20%">
            <el-select v-model="form.role" placeholder="请选择" style="width: 90%">
              <el-option label="教师 " value="ROLE_TEACHER">
              </el-option>
              <el-option label="学生 " value="ROLE_STUDENT">
              </el-option>
            </el-select>
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
        phone: '',
        pageNum: 1,
        pageSize: 5
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      form: {
      }
    }
  },
  //页面加载的时候，做一些事情，在create里面
  created() {
    this.findBySearch();
  },
  //定义一些页面上控件触发的事件调用的方法
  methods: {
    findBySearch() {
      request.get('/user/search', {
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
        phone: ''
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
      request.post('/user', this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      });
    },
    del(id) {
      request.delete('/user/' + id).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功',
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
    successUpload(res) {
      this.form.img = res.data;
    }
  },

}
</script>
