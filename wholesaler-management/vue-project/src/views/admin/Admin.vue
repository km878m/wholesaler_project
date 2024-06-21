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
      <el-table-column prop="sex" label="性别" width="80"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="phone" label="联系方式" ></el-table-column>
      <el-table-column prop="email" label="电子邮件" ></el-table-column>       
      <el-table-column prop="createTime" label="创建时间" ></el-table-column>      
      <el-table-column prop="updateTime" label="更新时间" ></el-table-column>

      <el-table-column label="状态">
        <template v-slot="scope">
          <!--scope.row就是当前行数据-->
          <el-switch v-model="scope.row.status" @change="changeStatus(scope.row)" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
        </template>
      </el-table-column>  

      <el-table-column label="操作" width="230">
        <template v-slot="scope">
          <!--scope.row就是当前行数据-->
          <el-button type="primary" @click="$router.push('/editadmin?id=' + scope.row.id)">编辑</el-button>
          <el-popconfirm title="您确定删除这行数据吗？" style="margin-left: 5px;" @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
          <el-button style="margin-left: 5px;" type="warning" @click="handleChangePass(scope.row)">修改密码</el-button>
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

     <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px" ref="formRef" :rules="rules">
        <el-form-item label="新密码" prop="newPass">
          <el-input v-model="form.newPass" autocomplete="off" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePass">确 定</el-button>
      </div>
    </el-dialog>

    </div>
  </template>
  
  <script>
  import request from "@/utils/request";
  import Cookies from 'js-cookie';

  export default {
    name: 'Admin',
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
        rules: {
          newPass: [
            { required: true, message: "请输入新密码", trigger: "blur" },
            { min: 3, max: 20, message: '长度在3-10个字符', trigger: 'blur'}
          ],
        }
      }
    },
    created(){
      this.load()
    },
    methods:{
      //打开修改密码弹窗方法
      handleChangePass(row){
        this.form = JSON.parse(JSON.stringify(row))
        this.dialogFormVisible = true
      },
      //修改密码方法
      savePass(){
        this.$refs['formRef'].validate((valid) => {
          if (valid) {
              request.put('/admin/password', this.form).then(res => {
                if (res.code === '200'){
                  this.$notify.success("修改成功")
                  if (this.form.id === this.user.id){  // 当前修改的用户id等于当前登录的管理员id，那么修改成功后需要重新登录
                    Cookies.remove('user')
                    this.$router.push('/login')
                  } else {
                    this.load()
                    this.dialogFormVisible = false
                  }
                } else {
                  this.$notify.error('修改失败')
                }
              })
          }
        })
      },
      // 加载列表数据方法
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
      // 刷新列表方法
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
      // 分页方法
      handleCurrentChange(pageNum){
        //点击分页按钮触发分页
        this.params.pageNum = pageNum
        this.load()
      },
      // 删除管理员方法
      del(id) {
        request.delete('/admin/delete/' + id).then(res => {
          if (res.code === '200'){
            this.$notify.success('删除成功')
            this.load();
          }else{
            this.$notify.error(res.msg)
          }
        })
      },
      //修改状态方法
      changeStatus(row){
        //不允许自己修改自己
        if(this.user.id === row.id){
          row.status = true
          this.$notify.warning('您的操作不合法')
          return
        }
        //更新管理员账号状态
        request.put('/admin/update', row).then(res =>{
                if(res.code === '200'){
                    this.$notify.success('操作成功')
                    this.load()
                }else{
                    this.$notify.error(res.msg)
                }
            })
      }
    }
  }
  </script>