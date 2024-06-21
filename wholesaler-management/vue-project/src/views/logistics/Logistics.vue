<template>
    <div>
      <!--搜索表单-->
     <div>
      <el-input style="width: 240px"  placeholder="请输入名称" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-button style="margin-left: 5px;" type="primary" @click="load"><i class="el-icon-search"></i> 搜索</el-button>
      <el-button style="margin-left: 5px;" type="warning" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
     </div>
  
     <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="name" label="名称" width="100"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="phone" label="联系方式" ></el-table-column>
      <el-table-column prop="frontMan" label="负责人" ></el-table-column>      
      <el-table-column prop="createTime" label="创建时间" ></el-table-column>      
      <el-table-column prop="updateTime" label="更新时间" ></el-table-column>      

      <el-table-column label="操作" width="200">
        <template v-slot="scope">
          <!--scope.row就是当前行数据-->
          <el-button type="primary" @click="$router.push('/editlogistics?id=' + scope.row.id)">编辑</el-button>
          <el-popconfirm title="您确定删除这行数据吗？"
            style="margin-left: 5px;"
            @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
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

    </div>
  </template>
  
  <script>
  import request from "@/utils/request";
  
  export default {
    name: 'Logistics',
    data(){
      return{
        tableData: [],
        total: 0,
        params:{
          pageNum:1,
          pageSize:10,
          name:'',
          phone:''
        }
      }
    },
    created(){
      this.load()
    },
    methods:{
      // 加载物流列表数据方法
      load(){
        request.get('/logistics/page',{
          params: this.params
        }).then(res => {
          if (res.code === '200'){
            this.tableData = res.data.list
            this.total = res.data.total
          }
        })
      },
      // 重置页面参数方法
      reset(){
        this.params = {
          pageNum:1,
          pageSize:10,
          name:'',
          phone:''
        }
        this.load()
      },
      // 更改页面数方法
      handleCurrentChange(pageNum){
        //点击分页按钮触发分页
        this.params.pageNum = pageNum
        this.load()
      },
      // 删除物流方法
      del(id) {
        request.delete('/logistics/delete/' + id).then(res => {
          if (res.code === '200'){
            this.$notify.success('删除成功')
            this.load();
          }else{
            this.$notify.error(res.msg)
          }
        })
      }
    }
  }
  </script>