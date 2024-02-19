<script setup lang="ts">
import 'vant/es/toast/style'
import { BarType } from "../models/bar";
import { barStatusEnum } from "../constants/bar";
import mouse from "../assets/bar.jpg";
import myAxios from "../plugins/myAxios";
import { showFailToast, showSuccessToast } from "vant";

import { useRouter } from "vue-router";

interface BarCardListProps {
  barList: BarType[];
}

const props = withDefaults(defineProps<BarCardListProps>(), {
  // @ts-ignore
  barList: [] as BarType[],
});

const router = useRouter();

/**
 * 加入队伍
 */
const doJoinBar = async (id: number) => {
  const res = await myAxios.post("/bar/join", {
    barId: id,
  });
  if (res?.code === 0) {
    showSuccessToast('加入成功');
  } else {
    showFailToast("加入失败" + (res.description ? `，${res.description}` : ""));
  }
};
</script>

<template>
  <div>
    <van-card
      v-for="bar in props.barList"
      :thumb="mouse"
      :desc="bar.description"
      :title="`${bar.name}`"
    >
      <template #tags>
        <van-tag plain type="danger" style="margin-right: 8px; margin-top: 8px">
          {{ barStatusEnum[bar.status] }}
        </van-tag>
      </template>
      <template #bottom>
        <div>
          {{ "创建时间: " + bar.createTime }}
        </div>
      </template>
      <template #footer>
        <van-button
          size="small"
          type="primary"
          plain
          @click="doJoinBar(bar.id)"
          >加入分区</van-button
        >
      </template>
    </van-card>
  </div>
</template>

<style scoped>
#barCardList :deep(.van-image__img) {
  height: 128px;
  object-fit: unset;
}
</style>
