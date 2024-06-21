<template>
    <div>
      <!--搜索表单-->
     <div>
      <el-input style="width: 240px"  placeholder="请输入姓名" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入用户名" v-model="params.username"></el-input>
      <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-button style="margin-left: 5px;" type="primary" @click="load"><i class="el-icon-search"></i> 搜索</el-button>
      <el-button style="margin-left: 5px;" type="warning" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
     </div>
  
     <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="name" label="名称" width="100"></el-table-column> 
      <el-table-column prop="phone" label="联系方式" ></el-table-column>   
      <el-table-column prop="updateTime" label="更新时间" ></el-table-column>
      <el-table-column prop="job" label="管理员权限" ></el-table-column>

      <el-table-column label="操作" width="230">
        <template v-slot="scope">
          <!--scope.row就是当前行数据-->
          <el-button style="margin-left: 5px;" type="danger" @click="handleChangeJob(scope.row)">修改权限</el-button>
        </template>
      </el-table-column>
     </el-table>
  
     <!--分页-->
     <div style="margin-top: 20px;">
      <el-pagination
        background
        :current-page="params.pageNum"
        :page-size="params.pageSize"
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :total="total">
      </el-pagination>
     </div>

     <el-dialog title="修改管理员权限" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px">
        <el-form-item label="管理员权限" prop="job">
          <el-select v-model="form.job" filterable placeholder="请选择" style="width: 150px;">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :value="item.value"
                  >
                  </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveJob">确 定</el-button>
      </div>
    </el-dialog>

    </div>
  </template>
  
  <script>
  import request from "@/utils/request";
  import Cookies from 'js-cookie';

  export default {
    name: 'AdminJob',
    data(){
      return{
        user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
        tableData: [],
        total: 0,
        form: {},
        dialogFormVisible: false,
        params:{
          pageNum:1,
          pageSize:10,
          name:'',
          username:'',
          phone:'',
        },
        options: [{
          value: '未分配',
        }, {
          value: '系统管理员',
        }, {
          value: '销售业务员',
        }, {
          value: '仓库管理员',
        }],
      }
    },
    created(){
      this.load()
    },
    methods:{
      // 打开管理员权限编辑弹窗
      handleChangeJob(row){
        this.form = JSON.parse(JSON.stringify(row))
        this.dialogFormVisible = true
      },
      // 编辑管理员权限方法
      saveJob(){
        //不允许修改自己的权限
        if(this.user.id === this.form.id){
          this.$notify.warning('您的操作不合法')
          return
        }
        request.put('/admin/job', this.form).then(res =>{
                if(res.code === '200'){
                    this.$notify.success('操作成功')
                    this.load()
                    this.dialogFormVisible = false
                }else{
                    this.$notify.error(res.msg)
                }
            })
      },
      load(){  
        request.get('/admin/page',{
          params: this.params
        }).then(res => {
          if (res.code === '200'){
            this.tableData = res.data.list
            this.total = res.data.total
          }
        })
      },
      reset(){
        this.params = {
          pageNum:1,
          pageSize:10,
          name:'',
          username:'',
          phone:''
        }
        this.load()
      },
      handleCurrentChange(pageNum){
        //点击分页按钮触发分页
        this.params.pageNum = pageNum
        this.load()
      },
    }
  }
  </script>