<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">新增物流</div>
    <el-form
      :inline="true"
      :model="form"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入物流名称"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="负责人" prop="frontMan">
        <el-input v-model="form.frontMan" placeholder="请输入负责人姓名"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center; margin-top: 30px">
      <el-button type="primary" @click="save" size="medium">提交</el-button>
      <!--<el-button type="danger" >取消</el-button>-->
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AddLogistics",
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
        callback(new Error("请输入合法手机号"));
      }
      callback()
    };
    return {
      form: {},
      rules: {
        name: [{ required: true, message: "请输入物流名称", trigger: "blur" }],
        address: [{ required: true, message: "请输入物流地址", trigger: "blur" }],
        phone: [{ validator: checkPhone, trigger: "blur" }],
        frontMan: [{ required: true, message: "请输入负责人姓名", trigger: "blur" }],
      },
    };
  },
  methods: {
    // 物流添加方法
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post("/logistics/save", this.form).then((res) => {
            if (res.code === "200") {
              this.$notify.success("新增成功");
              this.form = {};
            } else {
              this.$notify.error(res.msg);
            }
          });
        }
      });
    },
  },
};
</script>
