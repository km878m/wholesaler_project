<template>
    <div>
        <div style="margin-bottom: 30px">销售单生成</div>
        <div>
            <el-form :inline="true" :rules="rules" ref="ruleForm" :model="formI"  label-width="100px" size="mini">
              <el-form-item label="销售业务员" prop="adminName" >
                  <el-input disabled v-model="formI.adminName" style="width: 70px;"></el-input>
              </el-form-item>
              <el-form-item label="客户" prop="customerName">
                <el-select v-model="formI.customerName" filterable placeholder="请选择" style="width: 90px;">
                  <el-option
                    v-for="item in options"
                    :key="item.name  + item.phone"
                    :value="item.name + item.phone"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="物流" prop="logisticsName">
                <el-select v-model="formI.logisticsName" filterable placeholder="请选择" style="width: 120px;">
                  <el-option
                    v-for="item in optionsI"
                    :key="item.name + '(' + item.address + ')' "
                    :value="item.name + '(' + item.address + ')' "
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="销售单号" prop="sellNumber">
                  <el-input disabled v-model="formI.sellNumber" placeholder="销售单号"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="save">生成销售单</el-button>
              </el-form-item>
            </el-form>
        </div>
        
      

    </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';
  
export default {
    name: "CreateSellForm",
    data() {
      return {
        user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
        formI: {},
        options: [],
        optionsI: [],
        rules: {
            customerName: [{ required: true, message: "请输入客户姓名", trigger: "blur" }],
            logisticsName: [{ required: true, message: "请输入物流名称", trigger: "blur" }],
        },
      };
    },
    created() {
        this.formI.adminName = this.user.name
        request.get("/Customer/list").then(res => {
            this.options = res.data
        })
        request.get("/logistics/list").then(res => {
            this.optionsI = res.data
        })
    },
    methods: {
      save() {
        this.$refs['ruleForm'].validate((valid) => {
            if (valid) { 
                request.post("/sell/save", this.formI).then((res) => {
                    if (res.code === "200") {
                        this.$notify.success("销售单已创建");
                        this.$router.push('/addsellformitem?id=' + res.data)
                    } else {
                        this.$notify.error(res.msg);
                    }
                }); 
            }
        })
      },
    },
};
</script>