<script setup lang="ts">
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  const searchText = ref('');
  const router = useRouter();
  //标签列表，从后端拉取
  const tagList = ['标签1','标签2','标签3','标签4','标签5'];
  //过滤后的标签数组
  let filteredList = ref(tagList);
  const onSearch = () => {
    filteredList.value = tagList.filter(tag => tag.includes(searchText.value));
    console.log(filteredList.value);
  };
  const onCancel = () => {
    router.push('/')
  };
  const onClear = () => {
    searchText.value='';
  }

</script>

<template>
  <form action="/">
    <van-search
      v-model="searchText"
      show-action
      placeholder="搜索"
      @search="onSearch"
      @cancel="onCancel"
      @clear="onClear"
    />
  </form>


  <van-divider content-position="left">最近逛的吧</van-divider>
  <van-space><van-tag round type="primary" size="large" plain v-for="tag in tagList">{{ tag }}</van-tag></van-space>

  <van-divider content-position="left">搜索历史</van-divider>
  <van-space><van-tag round type="primary" size="large" plain v-for="tag in tagList">{{ tag }}</van-tag></van-space>

  <van-divider content-position="left">搜索发现</van-divider>
  <van-space><van-tag round type="primary" size="large" plain v-for="tag in tagList">{{ tag }}</van-tag></van-space>

</template>
  
<style scoped>

</style>