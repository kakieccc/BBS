<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import myAxios from "../plugins/myAxios";
import imgSrc from "../assets/login.jpg";
import { showFailToast } from "vant";
const userAccount = ref("");
const userPassword = ref("");
const router = useRouter();
const onSubmit = async () => {
  const res = await myAxios.post("/user/login", {
    userAccount: userAccount.value,
    userPassword: userPassword.value,
  });
  if (res?.code === 0 && res.data) {
    //登陆成功后跳转到主页
    router.replace("/");
  }
};
const onRegister = async () => {
  router.push("/user/register");
};
const onForget = () => {
  showFailToast("还没有实现这个功能喵");
}
</script>

<template>
  <div id="background">
    <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
  <div id="content">
    <div id="loginform">
      <h2 style="margin-left: 5%; color: #ffffff">Hello ,</h2>
      <h3 style="margin-left: 5%; color: #ffffff">Find you want !</h3>
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field
            label-width="3em"
            v-model="userAccount"
            name="userAccount"
            label="账号"
            placeholder="请输入账号"
            :rules="[{ required: true, message: '请填写账号' }]"
            required
          />
          <van-field
            label-width="3em"
            v-model="userPassword"
            type="password"
            name="userPassword"
            label="密码"
            placeholder="请输入密码"
            :rules="[{ required: true, message: '请填写密码' }]"
            required
          />
        </van-cell-group>
        <div style="margin: 16px">
          <van-button
            round
            block
            type="primary"
            native-type="submit"
            style="background-color: #828d91; opacity: 0.7"
          >
            登录
          </van-button>
        </div>
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
      <!-- todo:实现记住密码和找回密码功能 -->
      <a style="color: #1989fa; margin-left: 75%; text-decoration: underline;" @click="onForget">忘记密码？</a>
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
