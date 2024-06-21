<template>
    <div>
        <div style="margin-bottom: 30px">采购单生成</div>
        <div>
            <el-form :inline="true" :model="formI"  label-width="100px" size="mini">
              <el-form-item label="仓库管理员" prop="adminName" >
                  <el-input disabled v-model="formI.adminName" style="width: 70px;"></el-input>
              </el-form-item>
              <el-form-item label="采购单号" prop="orderNumber">
                  <el-input disabled v-model="formI.orderNumber" placeholder="采购单号"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="save">生成采购单</el-button>
              </el-form-item>
            </el-form>
        </div>
        
      

    </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';
  
export default {
    name: "CreateOrderForm",
    data() {
      return {
        user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
        formI: {},
      };
    },
    created() {
        this.formI.adminName = this.user.name
    },
    methods: {
      save() {
            request.post("/order/save", this.formI).then((res) => {
                if (res.code === "200") {
                    this.$notify.success("销售单已创建");
                    this.$router.push('/addorderformitem?id=' + res.data)
                } else {
                    this.$notify.error(res.msg);
                }
            }); 
      },
    },
};
</script>