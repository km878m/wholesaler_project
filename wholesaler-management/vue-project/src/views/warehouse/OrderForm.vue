<template>
    <div>
      <!--搜索表单-->
     <div>
      <el-input disabled style="width: 70px"  placeholder="请输入仓库管理员姓名" v-model="params.adminName"></el-input>
      <el-select style="width: 200px ; margin-left: 5px;" v-model="params.status" filterable placeholder="请选择销售单状态">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :value="item.value"
                  >
                  </el-option>
      </el-select>
      <el-button style="margin-left: 5px;" type="primary" @click="load"><i class="el-icon-search"></i> 搜索</el-button>
      <el-button style="margin-left: 5px;" type="warning" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
     </div>
  
     <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="orderNumber" label="销售单号" width="80"></el-table-column>
      <el-table-column prop="adminName" label="仓库管理员" width="100"></el-table-column>    
      <el-table-column prop="number" label="总件数" width="100"></el-table-column>    
      <el-table-column prop="totalPrices" label="总价"></el-table-column>
      <el-table-column prop="status" label="状态"></el-table-column>
      <el-table-column prop="createTime" label="采购单创建时间" ></el-table-column>      

      <el-table-column label="操作" width="280">
        <template v-slot="scope">
          <!--scope.row就是当前行数据-->
          <el-button v-if="scope.row.status === '创建中'" type="primary" @click="$router.push('/addorderformitem?id=' + scope.row.id)">编辑</el-button>
          <el-button v-if="scope.row.status === '已入库'" type="primary" @click="$router.push('/seeorderformitem?id=' + scope.row.id)">查看</el-button>
          <el-popconfirm title="您确定删除此采购单吗？" style="margin-left: 5px;" @confirm="del(scope.row.id)">
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

    </div>
  </template>
  
  <script>
  import request from "@/utils/request";
  import Cookies from 'js-cookie';

  export default {
    name: 'OrderForm',
    data(){
      return{
        user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
        options: [{
          value: '创建中',
        }, {
          value: '已入库',
        }],
        tableData: [],
        total: 0,
        params:{
          pageNum:1,
          pageSize:10,
          adminName:'',
          status:'',
        },
      }
    },
    created(){
      this.load()
    },
    methods:{
      load(){  
        this.params.adminName = this.user.name
        request.get('/order/page',{
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
          adminName:'',
          status:'',
        }
        this.load()
      },
      handleCurrentChange(pageNum){
        //点击分页按钮触发分页
        this.params.pageNum = pageNum
        this.load()
      },
      del(id) {
        request.delete('/order/delete/' + id).then(res => {
          if (res.code === '200'){
            this.$notify.success('删除成功')
            this.load();
          }else{
            this.$notify.error(res.msg)
          }
        })
      },
    }
  }
  </script>