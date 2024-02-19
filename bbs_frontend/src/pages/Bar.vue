<script setup lang="ts">
import 'vant/es/toast/style'
import { useRouter } from "vue-router";
import BarCardList from "../components/BarCardList.vue";
import { onMounted, ref } from "vue";
import myAxios from "../plugins/myAxios";
import { showFailToast } from "vant";

const router = useRouter();
const barList = ref([]);

onMounted(async () => {
  const res = await myAxios.get("/bar/list");
  if(res?.code === 0) {
    barList.value = res.data;
  } else {
    showFailToast("分区加载失败，请稍后重试")
  }

})
const doJoinBar = () => {
  router.push({
    path: "/bar/add",
  });
};

</script>

<template>
  <div id="barPage">
    <bar-card-list :barList="barList"></bar-card-list>
    <van-button type="primary" @click="doJoinBar">增加分区</van-button>
  </div>
</template>

<style scoped></style>
