<template>
  <div>
    <div style="margin-bottom: 12px;">
      <el-input style="width: 200px;" placeholder="请输入图书称" v-model="params.name" clearable>
      </el-input>
      <el-input style="width: 200px; margin-left: 5px;" placeholder="请输入图书作者" v-model="params.author" clearable>
      </el-input>
      <el-button style="margin-left: 10px;" type="warning" @click="findBySearch()">查询</el-button>
      <el-button style="margin-left: 10px;" type="primary" @click="reset()">清空</el-button>
      <el-button style="margin-left: 10px;" type="primary" @click="add()" v-if="user.role !== 'ROLE_STUDENT'">新增</el-button>
    </div>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="img" label="图书封面">
        <template v-slot="scope">
          <el-image style="width: 65px; height: 65px; border-radius: 50%;"
            :src="'http://localhost:8080/api/files/' + scope.row.img"
            :preview-src-list="['http://localhost:8080/api/files/' + scope.row.img]">
          </el-image>
        </template>
      </el-table-column>

      <el-table-column prop="name" label="图书名称">
      </el-table-column>
      <el-table-column prop="author" label="图书作者">
      </el-table-column>
      <el-table-column prop="price" label="图书价格">
      </el-table-column>
      <el-table-column prop="press" label="图书出版社">
      </el-table-column>
      <el-table-column prop="typeName" label="图书分类">
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" @click="edit(scope.row)" v-if="user.role === 'ROLE_ADMIN'">编辑</el-button>
          <el-button type="primary" @click="down(scope.row.img)">下载</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
            <el-button slot="reference" type="danger" style="margin-left: 5px;" v-if="user.role === 'ROLE_ADMIN'">删除</el-button>
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
          <el-form-item label="图书名称" label-width="25%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="图书封面" label-width="25%">
            <el-upload action="http://localhost:8080/api/files/upload" :on-success="successUpload">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="图书作者" label-width="25%">
            <el-input v-model="form.author" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="图书价格" label-width="25%">
            <el-input v-model="form.price" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="图书出版社" label-width="25%">
            <el-input v-model="form.press" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="图书分类" label-width="25%">
            <el-select v-model="form.typeId" placeholder="请选择" style="width: 90%">
              <el-option v-for="item in typeObjs" :key="item.id" :label="item.name" :value="item.id">
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
        author: '',
        pageNum: 1,
        pageSize: 5
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      form: {},
      typeObjs: [],
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {}
    }
  },
  //页面加载的时候，做一些事情，在create里面
  created() {
    this.findBySearch();
    this.findTypes()
  },
  //定义一些页面上控件触发的事件调用的方法
  methods: {
    findTypes() {
      request.get('/type').then(res => {
        if (res.code === '0') {
          this.typeObjs = res.data;
        } else {
          this.$message.error(res.msg)
        }
      });
    },
    findBySearch() {
      request.get('/book/search', {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error(res.msg);
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
        author: ''
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
      request.post('/book', this.form).then(res => {
        if (res.code === '0') {
          this.$message.success('操作成功');
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    del(id) {
      request.delete('/book/' + id).then(res => {
        if (res.code === '0') {
          this.$message.success('删除成功');
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    successUpload(res) {
      this.form.img = res.data;
    },
    down(flag) {
      location.href = 'http://localhost:8080/api/files/' + flag
    }
  },


}
</script>
