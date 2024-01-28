import Index from '../pages/Index.vue'
import Friend from '../pages/Friend.vue'
import Search from '../pages/Search.vue'
import Setting from '../pages/Setting.vue'
import Edit from '../pages/Edit.vue'

// 定义路由
// 每个路由都需要映射到一个组件。
const routes = [
  { path: '/', component: Index },
  { path: '/friend', component: Friend },
  { path: '/search', component: Search },
  { path: '/setting', component: Setting },
  { path: '/setting/edit/', component: Edit },
]

export default routes;