<script setup lang="ts">

import {useRouter} from "vue-router";
import BarCardList from "../components/BarCardList.vue";
import {onMounted, ref} from "vue";
import myAxios from "../plugins/myAxios";
import { showFailToast} from "vant";

const active = ref('public')
const router = useRouter();
const searchText = ref('');

const onTabChange = (name) => {
  // 查公开
  if (name === 'public') {
    listBar(searchText.value, 0);
  } else {
    // 查加密
    listBar(searchText.value, 2);
  }
}

// 跳转到创建分区页
const toAddBar = () => {
  router.push({
    path: "/bar/add"
  })
}

const barList = ref([]);

const listBar = async (val = '', status = 0) => {
  const res = await myAxios.get("/bar/list", {
    params: {
      searchText: val,
      pageNum: 1,
      status,
    },
  });
  if (res?.code === 0) {
    barList.value = res.data;
  } else {
    showFailToast('加载分区失败，请刷新重试');
  }
}

// 页面加载时只触发一次
onMounted( () => {
  listBar();
})

const onSearch = (val) => {
  listBar(val);
};

</script>

<template>
  <div id="barPage">
    <van-search v-model="searchText" placeholder="搜索分区" @search="onSearch" />
    <van-tabs v-model:active="active" @change="onTabChange">
      <van-tab title="公开" name="public" />
      <van-tab title="加密" name="private" />
    </van-tabs>
    <div style="margin-bottom: 16px" />
    <van-button class="add-button" type="primary" icon="plus" @click="toAddBar" />
    <bar-card-list :barList="barList" />
    <van-empty v-if="barList?.length < 1" description="数据为空"/>
  </div>
</template>

<style scoped>
#barPage {

}
</style>
