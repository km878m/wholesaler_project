<template>
  <div>
    <!--头部区域-->
    <div style=" height: 60px; line-height: 60px; background-color: #fff; margin-bottom: 2px; display: flex;">
      <div style="width: 400px;">
        <img src="@/assets/logo.png" style=" width: 40px; height: 40px; position: relative; top: 10px; left: 20px;"/>
        <span style="margin-left: 20px; font-size: 24px">实体商行管理系统</span>
      </div>
      <div style="flex: 1; text-align: right; padding-right: 20px;;">
        <el-dropdown size="medium" style="cursor: pointer;">
          <span class="el-dropdown-link">
            {{ user.name }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><span @click="logout">退出</span></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

    </div>

    <!--侧边栏和主体-->
    <div style="display: flex">
      <!--侧边栏导航-->
      <div
        style="
          width: 200px;
          min-height: calc(100vh);
          overflow: hidden;
          margin-right: 2px;
          background-color: #fff;
        "
      >
        <el-menu :default-active="$route.path" router class="el-menu-demo">
          <el-menu-item index="/">
            <i class="el-icon-eleme"></i>
            <span>首页</span>
          </el-menu-item>
          <el-submenu index="customer" v-if="user.job === '销售业务员'">
            <template slot="title">
              <i class="el-icon-s-custom"></i>
              <span>客户管理</span>
            </template>
            <el-menu-item index="/addcustomer">客户添加</el-menu-item>
            <el-menu-item index="/customerList">客户列表</el-menu-item>
          </el-submenu>
          <el-submenu index="logistics" v-if="user.job === '销售业务员'">
            <template slot="title">
              <i class="el-icon-truck"></i>
              <span>物流管理</span>
            </template>
            <el-menu-item index="/addlogistics">物流添加</el-menu-item>
            <el-menu-item index="/logisticsList">物流列表</el-menu-item>
          </el-submenu>
          <el-submenu index="sell" v-if="user.job === '销售业务员'">
            <template slot="title">
              <i class="el-icon-s-order"></i>
              <span>销售单管理</span>
            </template>
            <el-menu-item index="/createsellform">销售单生成</el-menu-item>
            <el-menu-item index="/sellformList">销售单列表</el-menu-item>
          </el-submenu>
          <el-submenu index="category" v-if="user.job === '仓库管理员'">
            <template slot="title">
              <i class="el-icon-s-operation"></i>
              <span>产品分类管理</span>
            </template>
            <el-menu-item index="/addcategory">产品大类添加</el-menu-item>
            <el-menu-item index="/categoryList">分类列表</el-menu-item>
          </el-submenu>
          <el-submenu index="product" v-if="user.job === '仓库管理员'">
            <template slot="title">
              <i class="el-icon-goods"></i>
              <span>产品管理</span>
            </template>
            <el-menu-item index="/addproduct">产品添加</el-menu-item>
            <el-menu-item index="/productList">产品列表</el-menu-item>
            <el-menu-item index="/productinwarehouseList">库存列表</el-menu-item>
          </el-submenu>
          <el-submenu index="transporter" v-if="user.job === '仓库管理员'">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>运输负责人管理</span>
            </template>
            <el-menu-item index="/addtransporter">运输负责人添加</el-menu-item>
            <el-menu-item index="/transporterList">运输负责人列表</el-menu-item>
          </el-submenu>
          <el-submenu index="outWarehouse" v-if="user.job === '仓库管理员'">
            <template slot="title">
              <i class="el-icon-truck"></i>
              <span>出库处理</span>
            </template>
            <el-menu-item index="/sellforminwarehouseList">出库列表</el-menu-item>
          </el-submenu>
          <el-submenu index="warehouse" v-if="user.job === '仓库管理员'">
            <template slot="title">
              <i class="el-icon-s-order"></i>
              <span>采购单管理</span>
            </template>
              <el-menu-item index="/createorderform">采购单生成</el-menu-item>
              <el-menu-item index="/orderformList">采购单列表</el-menu-item> 
          </el-submenu>
          <el-submenu index="admin" v-if="user.job === '系统管理员'">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>管理员管理</span>
            </template>
            <el-menu-item index="/addadmin">管理员添加</el-menu-item>
            <el-menu-item index="/adminList">管理员列表</el-menu-item>
            <el-menu-item index="/adminjob">管理员权限管理</el-menu-item>
          </el-submenu>
          <el-submenu index="operateLog" v-if="user.job === '系统管理员'">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span>操作日志管理</span>
            </template>
            <el-menu-item index="/operatelogList">操作日志查看</el-menu-item>
          </el-submenu>
        </el-menu>    
      </div>

      <!--主体数据-->
      <div style="flex: 1; background-color: #fff; padding: 10px">
        <router-view />
      </div>

      
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie';

export default {
  name: "Layout.vue",
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {}
    };
  },
  methods: {
    logout(){
      //清除游览器用户数据
      Cookies.remove('user')
      this.$router.push('/login')
    }
  },
};
</script>