import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'
import Cookies from 'js-cookie'

Vue.use(VueRouter)

const routes = [
  //==== Login ====
  {
    path: '/login',
    name: 'LOGIN',
    component: () => import('@/views/login/Login.vue')
  },
  //==== 主页 ====
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    children: [   //子路由
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/home/HomeView.vue')
      },
      // ==== Customer ====
      {
        path: 'customerList',
        name: 'Customer',
        component: () => import('@/views/customer/Customer.vue')
      },
      {
        path: 'addcustomer',
        name: 'AddCustomer',
        component: () => import('@/views/customer/AddCustomer.vue')
      },
      {
        path: 'editcustomer',
        name: 'EditCustomer',
        component: () => import('@/views/customer/EditCustomer.vue')
      },
      // ==== Admin ====
      {
        path: 'adminList',
        name: 'Admin',
        component: () => import('@/views/admin/Admin.vue')
      },
      {
        path: 'addadmin',
        name: 'AddAdmin',
        component: () => import('@/views/admin/AddAdmin.vue')
      },
      {
        path: 'editadmin',
        name: 'EditAdmin',
        component: () => import('@/views/admin/EditAdmin.vue')
      },
      {
        path: 'adminjob',
        name: 'AdminJob',
        component: () => import('@/views/admin/AdminJob.vue')
      },
      // ==== Category ====
      { path: 'categoryList', name: 'Category', component: () => import('@/views/category/Category.vue') },
      { path: 'addcategory', name: 'AddCategory', component: () => import('@/views/category/AddCategory.vue')},
      { path: 'editcategory', name: 'EditCategory', component: () => import('@/views/category/EditCategory.vue')},
      // ==== Product ====
      { path: 'productList', name: 'Product', component: () => import('@/views/product/Product.vue') },
      { path: 'addproduct', name: 'AddProduct', component: () => import('@/views/product/AddProduct.vue')},
      { path: 'editproduct', name: 'EditProduct', component: () => import('@/views/product/EditProduct.vue')},
      // ==== Sell ====
      { path: 'createsellform', name: 'CreateSellForm', component: () => import('@/views/sell/CreateSellForm.vue') },
      { path: 'addsellformitem', name: 'AddSellFormItem', component: () => import('@/views/sell/AddSellFormItem.vue') },
      { path: 'sellformList', name: 'SellForm', component: () => import('@/views/sell/SellForm.vue') },
      { path: 'seesellformitem', name: 'SeeSellFormItem', component: () => import('@/views/sell/SeeSellFormItem.vue') },
      // ==== Warehouse ====
      { path: 'sellforminwarehouseList', name: 'SellFormInWarehouse', component: () => import('@/views/warehouse/SellFormInWarehouse.vue') },
      { path: 'seesellformiteminwarehouse', name: 'SeeSellFormItemInWarehouse', component: () => import('@/views/warehouse/SeeSellFormItemInWarehouse.vue') },
      { path: 'createorderform', name: 'CreateOrderForm', component: () => import('@/views/warehouse/CreateOrderForm.vue') },
      { path: 'addorderformitem', name: 'AddOrderFormItem', component: () => import('@/views/warehouse/AddOrderFormItem.vue') },
      { path: 'orderformList', name: 'OrderForm', component: () => import('@/views/warehouse/OrderForm.vue') },
      { path: 'seeorderformitem', name: 'SeeOrderFormItem', component: () => import('@/views/warehouse/SeeOrderFormItem.vue') },
      { path: 'productinwarehouseList', name: 'ProductInWarehouse', component: () => import('@/views/warehouse/ProductInWarehouse.vue') },
      // ==== Transporter ====
      { path: 'transporterList', name: 'Transporter', component: () => import('@/views/transporter/Transporter.vue') },
      { path: 'addtransporter', name: 'AddTransporter', component: () => import('@/views/transporter/AddTransporter.vue') },
      { path: 'edittransporter', name: 'EditTransporter', component: () => import('@/views/transporter/EditTransporter.vue') },
      // ==== Logistics ====
      { path: 'logisticsList', name: 'Logistics', component: () => import('@/views/logistics/Logistics.vue') },
      { path: 'addlogistics', name: 'AddLogistics', component: () => import('@/views/logistics/AddLogistics.vue') },
      { path: 'editlogistics', name: 'EditLogistics', component: () => import('@/views/logistics/EditLogistics.vue') },
      // ==== OperateLog ====
      { path: 'operatelogList', name: 'OperateLog', component: () => import('@/views/operateLog/OperateLog.vue') },
    ]
  },
  {
    path: '*',
    component: () => import('@/views/404.vue')
  }
]

const router = new VueRouter({
  routes
})

//路由守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/login') next()
  const user = Cookies.get("user")
  if(!user && to.path !== '/login') return next("/login") //强制退回到登录界面
  // 访问 /home 的时候，并且cookie里面存在数据，这个时候我就直接放行
  next()
})

export default router
