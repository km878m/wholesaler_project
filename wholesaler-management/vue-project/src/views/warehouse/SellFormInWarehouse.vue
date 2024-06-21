<template>
    <div>
      <!--搜索表单-->
     <div>
      <el-input style="width: 200px"  placeholder="请输入销售业务员姓名" v-model="params.adminName"></el-input>
      <el-input style="width: 200px ; margin-left: 5px;"  placeholder="请输入客户姓名" v-model="params.customerName"></el-input>
      <el-input style="width: 200px ; margin-left: 5px;"  placeholder="请输入物流名称" v-model="params.logisticsName"></el-input>
      <el-select style="width: 200px ; margin-left: 5px;" v-model="params.status" filterable placeholder="请选择销售单状态">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :value="item.value"
                    :disabled="item.disabled"
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
      <el-table-column prop="totalPrices" label="总价"></el-table-column>
      <el-table-column prop="status" label="状态"></el-table-column>
      <el-table-column prop="createTime" label="订单创建时间" ></el-table-column>      
      <el-table-column prop="transporterName" label="运输负责人" ></el-table-column>      
      <el-table-column prop="outTime" label="出库时间" ></el-table-column>  

      <el-table-column label="操作" width="280">
        <template v-slot="scope">
          <!--scope.row就是当前行数据-->
          <el-button type="primary" @click="$router.push('/seesellformiteminwarehouse?id=' + scope.row.id)">查看</el-button>
          <el-button v-if="scope.row.status === '待发货'" type="success" @click="handle(scope.row)">发货</el-button>
          <el-popconfirm title="您确定删除这行数据吗？" style="margin-left: 5px;" @confirm="del(scope.row.id)">
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

     <el-dialog title="确定运输负责人" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form" label-width="100px" style="width: 85%;">
          <el-form-item label="运输负责人" prop="transporterName">
            <el-select v-model="form.transporterName" filterable placeholder="请选择" style="width: 100px;">
                  <el-option
                    v-for="item in optionsI"
                    :key="item.name  + item.phone"
                    :value="item.name + item.phone"
                  >
                  </el-option>
                </el-select>
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
    name: 'SellFormInWarehouse',
    data(){
      return{
        user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
        options: [{
          value: '创建中',
          disabled: true
        }, {
          value: '未支付',
          disabled: true
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
          status:'待发货',
        },
        dialogFormVisible: false,
        form:{},
        optionsI: [],
      }
    },
    created(){
      this.load()
    },
    methods:{
      load(){  
        request.get('sell/page',{
          params: this.params
        }).then(res => {
          if (res.code === '200'){
            this.tableData = res.data.list
            this.total = res.data.total
          }
        })
        request.get("/transporter/list").then(res => {
            this.optionsI = res.data
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
        this.dialogFormVisible = true
        this.form = row
      },
      update() {
        this.form.status = '发货完成'
        this.form.outTimeFlag = true
        request.post("/sell/update",this.form).then((res) => {
                    if (res.code === "200") {
                        this.$notify.success("确认发货成功");
                        this.load()
                    } else {
                        this.$notify.error(res.msg);
                    }
                }); 
                this.dialogFormVisible = false
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