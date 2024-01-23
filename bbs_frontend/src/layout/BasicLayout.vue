<script setup lang="ts">
    import { ref } from 'vue';
    import { showToast } from 'vant';
    import Index from "../pages/Index.vue";
    import Friend from "../pages/Friend.vue";

    const onClickLeft = () => history.back();
    const onClickRight = () => alert("右");

    const active = ref("index");
    const onChange = (index) => showToast(`标签 ${index}`);
</script>

<template>
    <van-nav-bar title="标题" left-arrow @click-left="onClickLeft" @click-right="onClickRight">
        <template #right>
            <van-icon name="search" size="18" />
        </template>
    </van-nav-bar>

    <div id="content">
      <template v-if="active === 'index'">
        <Index />
      </template>
      <template v-if="active === 'friend'">
        <Friend />
      </template>
    </div>

    <van-tabbar v-model="active">
        <van-tabbar-item icon="home-o" name="index">首页</van-tabbar-item>
        <van-tabbar-item icon="search" name="search">搜索</van-tabbar-item>
        <van-tabbar-item icon="friends-o" name="friend">好友</van-tabbar-item>
        <van-tabbar-item icon="setting-o" name="setting">设置</van-tabbar-item>
    </van-tabbar>


</template>

<style>

</style>