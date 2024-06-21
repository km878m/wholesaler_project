<template>
    <div style="width: 80%;">
        <div style="margin-bottom: 30px;">编辑产品</div>

        <el-form :inline="true" :rules="rules" ref="ruleForm" :model="form"  label-width="100px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入产品名称"></el-input>
            </el-form-item>
            <el-form-item label="规格" prop="description">
                <el-input v-model="form.description" placeholder="请输入产品规格"></el-input>
            </el-form-item>
            <el-form-item label="厂商" prop="manufacturer">
                <el-input v-model="form.manufacturer" placeholder="请输入产品生产厂商"></el-input>
            </el-form-item>
            <el-form-item label="分类" prop="category">
                <el-select v-model="form.category" filterable placeholder="请选择">
                    <el-option
                    v-for="item in options"
                    :key="item.name"
                    :value="item.name"
                    >
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="销售价格" prop="price">
                <el-input v-model="form.price" placeholder="请输入产品价格"></el-input>
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
    name: 'EditProduct',
    data(){
        return {
            form: {},
            options: [],
            rules: {
                name: [{ required: true, message: "请输入分类名称", trigger: "blur" }],
                description: [{ required: true, message: "请输入产品规格", trigger: "blur" }],
                manufacturer: [{ required: true, message: "请输入产品生产厂商", trigger: "blur" }],
                category: [{ required: true, message: "请输入产品分类", trigger: "blur" }],
                price: [{ required: true, message: "请输入产品价格", trigger: "blur" }],
            },
        }
    },
    created() {
        request.get("/category/list").then(res => {
            this.options = res.data
        })
        
        const id = this.$route.query.id
        request.get('/product/' + id).then(res => {
            this.form = res.data
        })
    },
    methods: {
        save(){
            this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                    request.put('/product/update', this.form).then(res =>{
                        if(res.code === '200'){
                            this.$notify.success('更新成功')
                            this.$router.push("/productList")
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