<template>
    <div style="width: 80%;">
        <div style="margin-bottom: 30px;">编辑产品分类</div>

        <el-form :inline="true" :rules="rules" ref="ruleForm" :model="form"  label-width="100px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入分类名称"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
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
    name: 'EditCategory',
    data(){
        return {
            form: {},
            rules: {
                name: [{ required: true, message: "请输入分类名称", trigger: "blur" }],
            },
        }
    },
    created() {
        const id = this.$route.query.id
        request.get('/category/' + id).then(res => {
            this.form = res.data
        })
    },
    methods: {
        save(){
            this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                    request.put('/category/update', this.form).then(res =>{
                        if(res.code === '200'){
                            this.$notify.success('更新成功')
                            this.$router.push("/categoryList")
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