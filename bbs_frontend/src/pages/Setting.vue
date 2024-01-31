<script setup lang="ts">
  import {useRouter} from "vue-router";
  const user = {
    id:1,
    userName:'kakie',
    userAccount:'123456',
    avatarUrl:null,
    gender:'男',
    phone:'111222333444',
    email:'xxx@xx.xx',
    userRole:0,
    tags:['python','java'],
    createtime:new Date()
  };
  const router = useRouter();
  const toEdit= (editKey: any,editName: any,currentValue: any) => {
    router.push({
        path:'/user/edit',
        query: {
            editKey,
            editName,
            currentValue
        }
    })
  }
</script>

<template>
  <van-cell title="账号" :value="user.userAccount"/>
  <van-cell title="头像" is-link to="/setting/edit" :value="user.avatarUrl" @click="toEdit('avatarUrl','头像',user.avatarUrl)"/>
  <van-cell title="昵称" is-link to="/setting/edit" :value="user.userName" @click="toEdit('userName','昵称',user.userName)"/>
  <van-cell title="性别" is-link to="/setting/edit" :value="user.gender" @click="toEdit('gender','性别',user.gender)"/>
  <van-cell title="电话" is-link to="/setting/edit" :value="user.phone" @click="toEdit('phone','电话',user.phone)"/>
  <van-cell title="邮箱" is-link to="/setting/edit" :value="user.email" @click="toEdit('email','邮箱',user.email)"/>
</template>
  
<style scoped>

</style>