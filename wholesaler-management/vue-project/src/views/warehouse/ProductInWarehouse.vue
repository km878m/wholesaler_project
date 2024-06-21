<template>
    <div>
      <!--搜索表单-->
     <div>
      <el-input style="width: 200px"  placeholder="请输入产品名称" v-model="params.name"></el-input>
      <el-input style="width: 200px ; margin-left: 5px;"  placeholder="请输入产品规格" v-model="params.description"></el-input>
      <el-input style="width: 200px ; margin-left: 5px;"  placeholder="请输入产品厂商" v-model="params.manufacturer"></el-input>
      <el-input style="width: 200px ; margin-left: 5px;"  placeholder="请输入产品分类" v-model="params.category"></el-input>
      <el-button style="margin-left: 5px;" type="primary" @click="load"><i class="el-icon-search"></i> 搜索</el-button>
      <el-button style="margin-left: 5px;" type="warning" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
     </div>
  
     <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="name" label="名称" width="100"></el-table-column>
      <el-table-column prop="description" label="规格"></el-table-column>     
      <el-table-column prop="manufacturer" label="厂商"></el-table-column>     
      <el-table-column prop="category" label="分类"></el-table-column>     
      <el-table-column prop="number" label="数量(件)"></el-table-column>     
      <el-table-column prop="createTime" label="创建时间" ></el-table-column>      
      <el-table-column prop="updateTime" label="更新时间" ></el-table-column>  

      <el-table-column label="操作" width="280">
        <template v-slot="scope">
          <!--scope.row就是当前行数据-->
          <el-button type="primary" @click="handleEdit(scope.row.id)">校验数量</el-button>
          <el-popconfirm title="您确定删除这行数据吗？" style="margin-left: 5px;" @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
     </el-table>
  
     <!--分页-->
     <div style="margin-top: 20px;" >
      <el-pagination
        background
        :current-page="params.pageNum"
        :page-size="params.pageSize"
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :total="total">
      </el-pagination>
     </div>

     <el-dialog title="校验数量" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form" label-width="100px" ref="ruleForm" :rules="rules" style="width: 85%;">
          <el-form-item label="数量" prop="number">
            <el-input v-model="form.number" autocomplete="off" style="width: 100px;"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="update">确 定</el-button>
        </div>
      </el-dialog>

    </div>
  </template>
  
  <script>
  import request from "@/utils/request";
  import Cookies from 'js-cookie';

  export default {
    name: 'ProductInWarehouse',
    data(){
      return{
        user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
        tableData: [],
        total: 0,
        params:{
          pageNum:1,
          pageSize:10,
          name:'',
          description:'',
          manufacturer:'',
          category:'',
        },
        dialogFormVisible: false,
        form: {},
        rules: {
            number: [{ required: true, message: "请输入产品数量", trigger: "blur" }],                                          
        },
      }
    },
    created(){
      this.load()
    },
    methods:{
      load(){  
        request.get('/product/page',{
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
          description:'',
          manufacturer:'',
          category:'',
        }
        this.load()
      },
      handleCurrentChange(pageNum){
        //点击分页按钮触发分页
        this.params.pageNum = pageNum
        this.load()
      },
      del(id) {
        request.delete('/product/delete/' + id).then(res => {
          if (res.code === '200'){
            this.$notify.success('删除成功')
            this.load();
          }else{
            this.$notify.error(res.msg)
          }
        })
      },
      handleEdit(id) {
        this.dialogFormVisible = true
        request.get('/product/' + id).then(res => {
            this.form = res.data
        })
      },
      update() {
        this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                    request.put('/product/update', this.form).then(res =>{
                        if(res.code === '200'){
                            this.$notify.success('核对成功')
                            this.dialogFormVisible = false
                            this.load()
                        }else{
                            this.$notify.error(res.msg)
                        }
                    })
                }
            })
      }
    }
  }
  </script>