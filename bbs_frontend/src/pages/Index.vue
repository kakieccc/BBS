<script setup lang="ts">
import 'vant/es/toast/style'
import { ref, watchEffect } from "vue";
import myAxios from "../plugins/myAxios";
import { showFailToast} from "vant";
import UserCardList from "../components/UserCardList.vue";
import { UserType } from "../models/user";

const userList = ref([]);
const loading = ref(true);

/**
 * 加载数据
 */
const loadData = async () => {
  let userListData;
  loading.value = true;
  // 普通模式，直接分页查询用户
  userListData = await myAxios
    .get("/user/recommend", {
      params: {
        pageSize: 8,
        pageNum: 1,
      },
    })
    .then(function (response) {
      console.log("/user/recommend succeed", response);
      return response?.data?.records;
    })
    .catch(function (error) {
      console.error("/user/recommend error", error);
      showFailToast("请求失败");
    });
  if (userListData) {
    userListData.forEach((user: UserType) => {
      if (user.tags) {
        user.tags = JSON.parse(user.tags);
      }
    });
    userList.value = userListData;
  }
  loading.value = false;
};

watchEffect(() => {
  loadData();
});
</script>

<template>
  <user-card-list :user-list="userList" :loading="loading" />
  <van-empty v-if="!userList || userList.length < 1" description="数据为空" />
</template>


<style scoped></style>
