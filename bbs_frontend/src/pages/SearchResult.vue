<script setup lang="ts">
  import { onMounted, ref } from 'vue';
  import { useRoute } from 'vue-router';
  import myAxios from '../plugins/myAxios.ts';
  import qs from 'qs';

  const route = useRoute();
  const { tags } = route.query;

  onMounted(() => {
    myAxios.get('/user/search/tags',{
      params: {
        tagNameList:tags
      },
      paramsSerializer: params => {
        return qs.stringify(params,{indices: false})
      }
    })  
    .then(function (response) {
      // 处理成功情况
      console.log('/user/search/tags succeed',response);
    })
    .catch(function (error) {
      // 处理错误情况
      console.error('/user/search/tags error',error);
    })
  })

  const mockUser = {
    id:12334,
    userName:'kakie',
    userAccount:'111111',
    avatarUrl:null,
    profile:'个人简介',
    gender:0,
    phone:'12312412414',
    email:'xxx@gg.aa',
    userRole:0,
    tags:['java', 'emo','test1111111111','自闭中'],
    createtime:new Date()
  }
  const userList = ref([mockUser]);
</script>

<template>
  <van-card
    v-for="user in userList"
    :desc="user.profile"
    :title="user.userName"
    :thumb="user.avatarUrl"
  >
    <template #tags>
      <van-tag plain type="danger" v-for="tag in user.tags" style="margin-right: 8px; margin-top: 8px"> {{ tag }}</van-tag>
    </template>
    <template #footer>
      <van-button size="mini">联系我</van-button>
    </template>
  </van-card>
</template>
  
<style scoped>

</style>