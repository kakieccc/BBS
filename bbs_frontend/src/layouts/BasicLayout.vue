<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import routes from "../config/route.ts";

const DEFAULT_TITLE = '伙伴匹配';
const title = ref(DEFAULT_TITLE);
const router = useRouter();
/**
 * 根据路由切换标题
 */
 router.beforeEach((to, from) => {
  const toPath = to.path;
  const route = routes.find((route) => {
    return toPath == route.path;
  })
  title.value = route?.title ?? DEFAULT_TITLE;
})

const onClickLeft = () => {
  router.back();
};
const onClickRight = () => {
  router.push("/search");
};


</script>

<template>
  <van-nav-bar
    :title="title"
    left-arrow
    @click-left="onClickLeft"
    @click-right="onClickRight"
  >
    <template #right>
      <van-icon name="search" size="18" />
    </template>
  </van-nav-bar>

  <div id="content">
    <router-view/>
  </div>
  <van-tabbar route>
    <van-tabbar-item icon="home-o" replace to="/" name="home"
      >首页</van-tabbar-item
    >
    <van-tabbar-item icon="search" replace to="/search" name="search"
      >搜索</van-tabbar-item
    >
    <van-tabbar-item icon="friends-o" replace to="/bar" name="bar"
      >分区</van-tabbar-item
    >
    <van-tabbar-item icon="setting-o" replace to="/user" name="setting"
      >个人</van-tabbar-item
    >
  </van-tabbar>
</template>

<style scoped>
#content {
  padding-bottom: 50px;
}
</style>
