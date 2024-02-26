import Index from "../pages/Index.vue";
import Bar from "../pages/Bar.vue";
import Search from "../pages/Search.vue";
import User from "../pages/User.vue";
import Edit from "../pages/Edit.vue";
import SearchResult from "../pages/SearchResult.vue";
import UserLogin from "../pages/UserLogin.vue";
import BarAdd from "../pages/BarAdd.vue";
import BarUpdate from "../pages/BarUpdate.vue";
import UserBarCreate from "../pages/UserBarCreate.vue";
import UserBarJoin from "../pages/UserBarJoin.vue";
import UserUpdate from "../pages/UserUpdate.vue";
import UserRegister from "../pages/UserRegister.vue";


// 定义路由
// 每个路由都需要映射到一个组件。
const routes = [
  { path: "/", component: Index },
  { path: "/bar", component: Bar ,title:"吧"},
  { path: "/search", component: Search ,title:"搜索"},
  { path: "/user", component: User ,title:"用户"},
  { path: "/user/edit/", component: Edit ,title:"编辑用户"},
  { path: "/user/list", component: SearchResult ,title:"用户列表"},
  { path: "/user/login", component: UserLogin ,title:"登录"},
  { path: "/user/register", component: UserRegister ,title:"注册"},
  { path: "/user/update", component: UserUpdate ,title:"更新用户"},
  { path: "/user/bar/join", component: UserBarJoin ,title:"加入贴吧"},
  { path: "/user/bar/create", component: UserBarCreate ,title:"创建贴吧"},
  { path: "/bar/add", component: BarAdd ,title:"创建贴吧"},
  { path: "/bar/update", component: BarUpdate},

];

export default routes;
