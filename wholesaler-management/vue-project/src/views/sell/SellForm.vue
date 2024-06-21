<template>
    <div>
      <!--搜索表单-->
     <div>
      <el-input disabled style="width: 70px"  placeholder="请输入业务员姓名" v-model="params.adminName"></el-input>
      <el-input style="width: 200px ; margin-left: 5px;"  placeholder="请输入客户姓名" v-model="params.customerName"></el-input>
      <el-input style="width: 200px ; margin-left: 5px;"  placeholder="请输入物流名称" v-model="params.logisticsName"></el-input>
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
      <el-table-column prop="sellNumber" label="销售单号" width="80"></el-table-column>
      <el-table-column prop="adminName" label="销售业务员" width="100"></el-table-column>
      <el-table-column prop="customerName" label="客户姓名"></el-table-column>     
      <el-table-column prop="logisticsName" label="物流名称"></el-table-column>        
      <el-table-column prop="number" label="总件数"></el-table-column>        
      <el-table-column prop="totalPrices" label="总价"></el-table-column>
      <el-table-column prop="status" label="状态"></el-table-column>
      <el-table-column prop="createTime" label="订单创建时间" ></el-table-column>      
      <el-table-column prop="transporterName" label="运输负责人" ></el-table-column>      
      <el-table-column prop="outTime" label="出库时间" ></el-table-column>  

      <el-table-column label="操作" width="280">
        <template v-slot="scope">
          <!--scope.row就是当前行数据-->
          <el-button v-if="scope.row.status === '创建中'||scope.row.status === '未支付'" type="primary" @click="$router.push('/addsellformitem?id=' + scope.row.id)">编辑</el-button>
          <el-button v-if="scope.row.status === '待发货'||scope.row.status === '发货完成'" type="primary" @click="$router.push('/seesellformitem?id=' + scope.row.id)">查看</el-button>
          <el-popconfirm title="您确定已经接收到了销售单款吗？" style="margin-left: 5px;" @confirm="handle(scope.row)">
            <el-button v-if="scope.row.status === '未支付'" type="success" slot="reference">确认支付</el-button>
          </el-popconfirm>
          <el-popconfirm title="您确定删除此销售单吗？" style="margin-left: 5px;" @confirm="del(scope.row.id)">
            <el-button v-if="scope.row.status !== '发货完成'" type="danger" slot="reference">删除</el-button>
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
    name: 'SellForm',
    data(){
      return{
        user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
        options: [{
          value: '创建中',
        }, {
          value: '未支付',
        }, {
          value: '待发货',
        }, {
          value: '发货完成',
        }],
        tableData: [],
        total: 0,
        params:{
          pageNum:1,
          pageSize:10,
          adminName:'',
          customerName:'',
          logisticsName:'',
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
        request.get('/sell/page',{
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
          customerName:'',
          logisticsName:'',
          status:'',
          createTime:'',
        }
        this.load()
      },
      handleCurrentChange(pageNum){
        //点击分页按钮触发分页
        this.params.pageNum = pageNum
        this.load()
      },
      handle(row) {
        row.status = '待发货'
        request.post("/sell/update",row).then((res) => {
                    if (res.code === "200") {
                        this.$notify.success("确认支付成功");
                        this.load()
                    } else {
                        this.$notify.error(res.msg);
                    }
                }); 
      },
      del(id) {
        request.delete('/sell/delete/' + id).then(res => {
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