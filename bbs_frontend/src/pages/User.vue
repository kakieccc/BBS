<script setup lang="ts">
import { useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import { getCurrentUser } from "../services/user";

const user = ref();
const router = useRouter();
onMounted(async () => {
  user.value = await getCurrentUser();
})
const toEdit = (editKey: any, editName: any, currentValue: any) => {
  router.push({
    path: "/user/edit",
    query: {
      editKey,
      editName,
      currentValue,
    },
  });
};
</script>

<template>
  <template v-if="user">
    <van-cell title="账号" :value="user?.userAccount"/>
    <van-cell title="昵称" is-link to="/user/edit" :value="user?.userName" @click="toEdit('userName', '昵称', user.userName)"/>
    <van-cell title="头像" is-link to="/user/edit" @click="toEdit('avatarUrl', '头像', user.avatarUrl)">
      <img style="height: 48px" :src="user?.avatarUrl"/>
    </van-cell>
    <van-cell title="性别" is-link to="/user/edit" :value="user?.gender" @click="toEdit('gender', '性别', user.gender)"/>
    <van-cell title="电话" is-link to="/user/edit" :value="user?.phone" @click="toEdit('phone', '电话', user.phone)"/>
    <van-cell title="邮箱" is-link to="/user/edit" :value="user?.email" @click="toEdit('email', '邮箱', user.email)"/>
  </template>
  <template v-else>
    <van-empty image="error" description="啊噢，出错了" />
  </template>
</template>

<style scoped></style>
