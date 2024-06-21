<template>
    <div>
      <div style="margin-bottom: 30px">销售单查看</div>
      <div>
            <el-form :inline="true" :model="formI"  label-width="100px" size="mini">
              <el-form-item label="销售业务员" prop="adminName" >
                  <el-input disabled v-model="formI.adminName" style="width: 70px;"></el-input>
              </el-form-item>
              <el-form-item label="客户" prop="customerName">
                  <el-input disabled v-model="formI.customerName" style="width: 130px;"></el-input> 
              </el-form-item>
              <el-form-item label="物流" prop="logisticsName">
                  <el-input disabled v-model="formI.logisticsName" placeholder="请输入物流名称" style="width: 120px;"></el-input>
              </el-form-item>
              <el-form-item label="销售单号" prop="sellNumber">
                  <el-input disabled v-model="formI.sellNumber" placeholder="销售单号"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="success" @click="$router.push('/sellformList')">返回</el-button>
              </el-form-item>
            </el-form>
      </div>
        
      <el-table :data="tableData" stripe>
        <el-table-column prop="productName" label="名称"></el-table-column>
        <el-table-column prop="productDescription" label="规格"></el-table-column>
        <el-table-column prop="number" label="数量（件）"></el-table-column>     
        <el-table-column prop="price" label="单价（元）"></el-table-column>       
        <el-table-column prop="totalPrices" label="合计（元）"></el-table-column>    
      </el-table>

    </div>
</template>

<script>
import request from "@/utils/request"
  
export default {
    name: "SeeSellFormItem",
    data() {
      return {
        formI: {},
        tableData: [],
      };
    },
    created() {
        this.load()
    },
    methods: {
      load(){  
        const id = this.$route.query.id
        request.get('/sell/' + id).then(res => {
          if (res.code === '200'){
            this.formI = res.data
            this.tableData = res.data.children
          }
        })
      },
    },
};
</script>