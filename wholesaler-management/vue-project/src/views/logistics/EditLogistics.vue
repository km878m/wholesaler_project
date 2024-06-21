<template>
    <div style="width: 80%;">
        <div style="margin-bottom: 30px;">编辑物流</div>
        <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm"  label-width="100px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
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
        <div style="text-align: center; margin-top:  30px;">
            <el-button type="primary" @click="save" size="medium">提交</el-button>
            <!--<el-button type="danger" >取消</el-button>-->
        </div>

    </div>
</template>

<script>
import request from "@/utils/request";

 export default {
    name: 'EditLogistics',
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
                name: [{ required: true, message: "请输入物流名称", trigger: "blur" }],
                address: [{ required: true, message: "请输入物流地址", trigger: "blur" }],
                phone: [{ validator: checkPhone, trigger: "blur" }],
                frontMan: [{ required: true, message: "请输入负责人姓名", trigger: "blur" }],
            },
        }
    },
    created() {
        const id = this.$route.query.id
        request.get('/logistics/' + id).then(res => {
            this.form = res.data
        })
    },
    methods: {
        save(){
            this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                    request.put('/logistics/update', this.form).then(res =>{
                        if(res.code === '200'){
                            this.$notify.success('更新成功')
                            this.$router.push("/logisticsList")
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