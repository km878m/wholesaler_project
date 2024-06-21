<template>
    <div style="width: 80%;">
        <div style="margin-bottom: 30px;">编辑客户</div>
        <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm"  label-width="100px">
            <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="性别" >
                <el-input v-model="form.sex" placeholder="请输入性别"></el-input>
            </el-form-item>
            <el-form-item label="地址">
                <el-input v-model="form.address" placeholder="请输入地址"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
            </el-form-item>
            <el-form-item label="电子邮箱">
                <el-input v-model="form.email" placeholder="请输入电子邮箱"></el-input>
            </el-form-item>
        </el-form>
        <div style="text-align: center; margin-top:  30px;">
            <el-button type="primary" @click="save" size="medium">提交</el-button>
            <!--<el-button type="danger" >取消</el-button>-->
        </div>

    </div>
</template>

<script>
import request from "@/utils/request";

 export default {
    name: 'EditCustomer',
    data(){
        const checkPhone = (rule, value, callback) => {
            if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
            callback(new Error("请输入合法手机号"));
            }
            callback()
        };
        return {
            form: {},
            rules: {
                name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
                phone: [{ validator: checkPhone, trigger: "blur" }],
            },
        }
    },
    created() {
        const id = this.$route.query.id
        request.get('/Customer/' + id).then(res => {
            this.form = res.data
        })
    },
    methods: {
        save(){
            this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                    request.put('/Customer/update', this.form).then(res =>{
                        if(res.code === '200'){
                            this.$notify.success('更新成功')
                            this.$router.push("/customerList")
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