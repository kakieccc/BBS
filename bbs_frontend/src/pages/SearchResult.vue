<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import myAxios from "../plugins/myAxios.ts";
import qs from "qs";
import { showToast } from "vant";

const route = useRoute();
const { tags } = route.query;
const userList = ref([]);

onMounted(async () => {
  const userListData = await myAxios.get('/user/search/tags', {
    params: {
      tagNameList: tags
    },
    paramsSerializer: params => {
      return qs.stringify(params, {indices: false})
    }
  })
      .then(function (response) {
        console.log('/user/search/tags succeed', response);
        return response?.data;
      })
      .catch(function (error) {
        console.error('/user/search/tags error', error);
        showToast('请求失败');
      })
  console.log(userListData)
  if (userListData) {
    userListData.forEach(user => {
      if (user.tags) {
        user.tags = JSON.parse(user.tags);
      }
    })
    userList.value = userListData;
  }
})

</script>

<template>
  <van-card
    v-for="user in userList"
    :desc="user.profile"
    :title="user.userName"
    :thumb="user.avatarUrl"
  >
    <template #tags>
      <van-tag
        plain
        type="danger"
        v-for="tag in user.tags"
        style="margin-right: 8px; margin-top: 8px"
      >
        {{ tag }}</van-tag
      >
    </template>
    <template #footer>
      <van-button size="mini">联系我</van-button>
    </template>
  </van-card>
  <!-- 搜索提示 -->
  <van-empty
    image="search"
    description="找不到对应标签的用户"
    v-if="!userList || userList.length < 1"
  />
</template>

<style scoped></style>