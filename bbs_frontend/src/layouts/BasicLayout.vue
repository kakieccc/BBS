<script setup lang="ts">
import { useRouter } from "vue-router";

const router = useRouter();
const onClickLeft = () => {
  router.back();
};
const onClickRight = () => {
  router.push("/search");
};
</script>

<template>
  <van-nav-bar
    title="标题"
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
    <van-tabbar-item icon="friends-o" replace to="/friend" name="friend"
      >好友</van-tabbar-item
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
