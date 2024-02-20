<script setup>
import { useRoute, useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import myAxios from "../plugins/myAxios.ts";
import { Toast } from "vant";

const router = useRouter();
const route = useRoute();

const addBarData = ref({});

const id = route.query.id;
//获取之前的分区信息
onMounted(async () => {
  if (id <= 0) {
    showFailToast("加载分区失败，请重试");
    return;
  }
  const res = await myAxios.get("/bar/get", {
    params: {
      id,
    },
  });
  if (res?.code === 0) {
    addBarData.value = res.data;
  } else {
    showFailToast("加载分区失败，请重试");
  }
});

//提交
const onSubmit = async () => {
  const postData = {
    ...addBarData.value,
    status: Number(addBarData.value.status),
  };
  //todo 前端数据校验
  const res = await myAxios.post("/bar/update", postData);
  if (res?.code === 0 && res.data) {
    Toast.success("更新成功");
    router.push({
      path: "/bar",
      replace: true,
    });
  } else {
    Toast.fail("更新失败");
  }
};
</script>

<template>
  <div id="barPage">
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model="addBarData.name"
          name="name"
          label="分区名"
          placeholder="请输入分区名"
          :rules="[{ required: true, message: '请输入分区名' }]"
        />
        <van-field
          v-model="addBarData.description"
          rows="4"
          autosize
          label="分区描述"
          type="textarea"
          placeholder="请输入分区描述"
        />
        <van-field name="radio" label="分区状态">
          <template #input>
            <van-radio-group
              v-model="addBarData.status"
              direction="horizontal"
            >
              <van-radio name="0">公开</van-radio>
              <van-radio name="1">私有</van-radio>
              <van-radio name="2">加密</van-radio>
            </van-radio-group>
          </template>
        </van-field>
        <van-field
          v-if="Number(addBarData.status) === 2"
          v-model="addBarData.password"
          type="password"
          name="password"
          label="密码"
          placeholder="请输入分区密码"
          :rules="[{ required: true, message: '请填写密码' }]"
        />
      </van-cell-group>
      <div style="margin: 16px">
        <van-button round block type="primary" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>
  </div>
</template>



<style scoped></style>
