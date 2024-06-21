<template>
    <div>
      <div style="margin-bottom: 30px">采购单查看</div>
      <div>
            <el-form :inline="true" :model="formI"  label-width="100px" size="mini">
              <el-form-item label="仓库管理员" prop="adminName" >
                  <el-input disabled v-model="formI.adminName" style="width: 70px;"></el-input>
              </el-form-item>
              <el-form-item label="采购单号" prop="orderNumber">
                  <el-input disabled v-model="formI.orderNumber" placeholder="采购单号"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="success" @click="$router.push('/orderformList')">返回</el-button>
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
    name: "SeeOrderFormItem",
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
        request.get('/order/' + id).then(res => {
          if (res.code === '200'){
            this.formI = res.data
            this.tableData = res.data.children
          }
        })
      },
    },
};
</script>