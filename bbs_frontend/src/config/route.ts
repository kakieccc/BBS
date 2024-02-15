import Index from "../pages/Index.vue";
import Friend from "../pages/Friend.vue";
import Search from "../pages/Search.vue";
import User from "../pages/User.vue";
import Edit from "../pages/Edit.vue";
import SearchResult from "../pages/SearchResult.vue";
import UserLogin from "../pages/UserLogin.vue";

// 定义路由
// 每个路由都需要映射到一个组件。
const routes = [
  { path: "/", component: Index },
  { path: "/friend", component: Friend },
  { path: "/search", component: Search },
  { path: "/user", component: User },
  { path: "/user/edit/", component: Edit },
  { path: "/user/list", component: SearchResult },
  { path: "/user/login", component: UserLogin },
];

export default routes;
