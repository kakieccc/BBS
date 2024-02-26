<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import myAxios from "../plugins/myAxios";
import imgSrc from "../assets/login.jpg";
import { showFailToast, showSuccessToast } from "vant";
const userName = ref("");
const userAccount = ref("");
const userPassword = ref("");
const checkPassword = ref("");
const router = useRouter();
const onRegister = async () => {
  const res = await myAxios.post("/user/register", {
    userName: userName.value,
    userAccount: userAccount.value,
    userPassword: userPassword.value,
    checkPassword: checkPassword.value,
  });
  if (res?.code === 0 && res.data) {
    //注册成功后跳转到登录页
    showSuccessToast("注册成功!");
    setTimeout(() => {
    router.replace("/user/login");
  }, 3000);
  } else {
    showFailToast(res.description);
  }
};
</script>

<template>
  <div id="background">
    <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
  <div id="content">
    <div id="loginform">
      <h2 style="margin-left: 5%; color: #ffffff">Hey ,</h2>
      <h3 style="margin-left: 5%; color: #ffffff">Welcome to join us !</h3>
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field
            v-model="userName"
            name="userName"
            label="名称"
            placeholder="请输入名称"
            :rules="[{ required: true, message: '请填写名称' }]"
          />
          <van-field
            v-model="userAccount"
            name="userAccount"
            label="账号"
            placeholder="请输入账号"
            :rules="[{ required: true, message: '请填写账号' }]"
          />
          <van-field
            v-model="userPassword"
            type="password"
            name="userPassword"
            label="密码"
            placeholder="请输入密码"
            :rules="[{ required: true, message: '请填写密码' }]"
          />
          <van-field
            v-model="checkPassword"
            type="password"
            name="checkPassword"
            label="校验密码"
            placeholder="请二次输入密码"
            :rules="[{ required: true, message: '请二次输入密码' }]"
          />
        </van-cell-group>
      </van-form>
      <div style="margin: 16px">
        <van-button
          round
          block
          type="primary"
          native-type="submit"
          style="background-color: #828d91; opacity: 0.7"
          @click="onRegister"
        >
          注册
        </van-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
#background {
  width: 100%;
  height: 100%;
  z-index: -1;
  position: absolute;
  object-fit: cover; /* 保持图片比例填充容器 */
}

#content {
  width: 100%;
  z-index: 1;
  position: absolute;
}

#loginform {
  width: 100%;
  position: absolute;
  margin-top: 40%;
  backdrop-filter: blur(3px);
}
</style>
