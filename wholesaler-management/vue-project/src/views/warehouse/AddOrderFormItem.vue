<template>
    <div>
      <div style="margin-bottom: 30px">采购单编辑</div>
      <div>
            <el-form :inline="true" :rules="rules" ref="ruleForm" :model="formI"  label-width="100px" size="mini">
              <el-form-item label="仓库管理员" prop="adminName" >
                  <el-input disabled v-model="formI.adminName" style="width: 70px;"></el-input>
              </el-form-item>
              <el-form-item label="采购单号" prop="orderNumber">
                  <el-input disabled v-model="formI.orderNumber" placeholder="采购单号"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="handleAdd">添加单项</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="success" @click="save">完成</el-button>
                <el-button type="warning" @click="$router.push('/createorderform')">暂存</el-button>
                <el-button type="danger" @click="del">取消此次采购</el-button>
              </el-form-item>
            </el-form>
      </div>
        
      <el-table :data="tableData" stripe>
        <el-table-column prop="productName" label="名称"></el-table-column>
        <el-table-column prop="productDescription" label="规格"></el-table-column>
        <el-table-column prop="number" label="数量（件）"></el-table-column>     
        <el-table-column prop="price" label="单价（元）"></el-table-column>       
        <el-table-column prop="totalPrices" label="合计（元）"></el-table-column>
        <el-table-column label="操作" width="280">
          <template v-slot="scope">
          <!--scope.row就是当前行数据-->
            <el-button type="primary" @click="handleEdit(scope.row.id)">编辑</el-button>
            <el-popconfirm title="您确定删除此项吗？" style="margin-left: 5px;" @confirm="delItem(scope.row.id)">
              <el-button type="danger" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>     
      </el-table>

      <el-dialog title="单项添加" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form" label-width="100px" ref="ruleForm" :rules="rules" style="width: 85%;">
          <el-form-item el-form-item label="产品名称" prop="productName">
                <el-select v-model="form.productName" filterable placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.name"
                    :value="item.name"
                  >
                  </el-option>
                </el-select>
          </el-form-item>
          <el-form-item label="数量" prop="number">
            <el-input v-model="form.number" autocomplete="off" style="width: 100px;"></el-input>
          </el-form-item>
          <el-form-item label="进货单价" prop="price">
            <el-input v-model="form.price" autocomplete="off" style="width: 100px;"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveItem">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="编辑" :visible.sync="dialogFormVisibleI" width="30%">
        <el-form :model="form" label-width="100px" ref="ruleFormI" :rules="rulesI" style="width: 85%;">
          <el-form-item label="数量" prop="number">
            <el-input v-model="form.number" autocomplete="off" style="width: 100px;"></el-input>
          </el-form-item>
          <el-form-item label="进货单价" prop="price">
            <el-input v-model="form.price" autocomplete="off" style="width: 100px;"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisibleI = false">取 消</el-button>
          <el-button type="primary" @click="updateItem">确 定</el-button>
        </div>
      </el-dialog>

    </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';
  
export default {
    name: "AddOrderFormItem",
    data() {
      return {
        formI: {},
        form: {},
        dialogFormVisible: false,
        dialogFormVisibleI: false,
        options: [],
        tableData: [],
        rules: {
            productName: [{ required: true, message: "请选择产品名称", trigger: "blur" }],
            number: [{ required: true, message: "请输入产品数量", trigger: "blur" }],                      
            price: [{ required: true, message: "请输入产品单价(进货价)", trigger: "blur" }],                      
        },
        rulesI: {
            number: [{ required: true, message: "请输入产品数量", trigger: "blur" }],            
            price: [{ required: true, message: "请输入产品单价(进货价)", trigger: "blur" }],            
        },
      };
    },
    created() {
        this.load()
    },
    methods: {
      del() {
        const id = this.$route.query.id
        request.delete('/order/delete/' + id).then(res => {
          if (res.code === '200'){
            this.$notify.success('采购单已销毁')
            this.$router.push('/createorderform')
          }else{
            this.$notify.error(res.msg)
          }
        })
      },
      delItem(id) {
        request.delete('/order/item/delete/' + id).then(res => {
          if (res.code === '200'){
            this.$notify.success('删除成功')
            this.load();
          }else{
            this.$notify.error(res.msg)
          }
        })
      },
      save() {
        this.formI.status = '已入库'
        request.post("/order/update", this.formI).then((res) => {
                    if (res.code === "200") {
                        this.$notify.success("采购单已生成");
                    } else {
                        this.$notify.error(res.msg);
                    }
                }); 
        this.$router.push("/createorderform")
      },
      handleAdd() {
        this.form = {}
        this.dialogFormVisible = true
      },
      handleEdit(id) {
        this.dialogFormVisibleI = true
        request.get("/order/item/" + id).then(res => {
            this.form = res.data
        })
      },
      load(){  
        const id = this.$route.query.id
        request.get("/product/list").then(res => {
            this.options = res.data
        })
        request.get('/order/' + id).then(res => {
          if (res.code === '200'){
            this.formI = res.data
            this.tableData = res.data.children
          }
        })
      },
      saveItem() {
        this.$refs['ruleForm'].validate((valid) => {
            if (valid) { 
                this.form.pid = this.$route.query.id
                request.post("/order/item/save", this.form).then((res) => {
                    if (res.code === "200") {
                        this.$notify.success("单项已添加");
                        this.form = {}
                        this.dialogFormVisible = false
                        this.load()
                    } else {
                        this.$notify.error(res.msg);
                    }
                }); 
            }
        })
      },
      updateItem() {
        this.$refs['ruleFormI'].validate((valid) => {
          if (valid) { 
                request.put("/order/item/update", this.form).then((res) => {
                    if (res.code === "200") {
                        this.$notify.success("编辑成功");
                        this.form = {}
                        this.dialogFormVisibleI = false
                        this.load()
                    } else {
                        this.$notify.error(res.msg);
                    }
                }); 
            }
        })
      }
    },
};
</script>