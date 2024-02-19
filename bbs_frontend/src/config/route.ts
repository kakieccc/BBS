import Index from "../pages/Index.vue";
import Bar from "../pages/Bar.vue";
import Search from "../pages/Search.vue";
import User from "../pages/User.vue";
import Edit from "../pages/Edit.vue";
import SearchResult from "../pages/SearchResult.vue";
import UserLogin from "../pages/UserLogin.vue";
import BarAdd from "../pages/BarAdd.vue";

// 定义路由
// 每个路由都需要映射到一个组件。
const routes = [
  { path: "/", component: Index },
  { path: "/bar", component: Bar },
  { path: "/search", component: Search },
  { path: "/user", component: User },
  { path: "/user/edit/", component: Edit },
  { path: "/user/list", component: SearchResult },
  { path: "/user/login", component: UserLogin },
  { path: "/bar/add", component: BarAdd},
];

export default routes;
