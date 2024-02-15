<script setup lang="ts">
import { useRoute, useRouter } from "vue-router";
import { ref } from "vue";
import myAxios from "../plugins/myAxios";
import { showFailToast, showSuccessToast } from "vant";
import {getCurrentUser} from "../services/user";

const route = useRoute();
const router = useRouter();

const editUser = ref({
  editKey: route.query.editKey,
  currentValue: route.query.currentValue,
  editName: route.query.editName,
});

const onSubmit = async () => {
  const currentUser = await getCurrentUser();
  if(!currentUser) {
    return;
  }
  const res = await myAxios.post("/user/update", {
    id: currentUser.id,
    [editUser.value.editKey as string]: editUser.value.currentValue,
  });
  if (res.data > 0) {
    showSuccessToast("修改成功");
    router.back();
  } else {
    showFailToast("修改失败");
  }
};
</script>

<template>
  <van-form @submit="onSubmit">
    <van-field
      v-model="editUser.currentValue"
      :name="editUser.editKey"
      :label="editUser.editName"
    />
    <div style="margin: 16px">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>
</template>

<style></style>
