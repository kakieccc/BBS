<script setup lang="ts">
import { ref } from "vue";
import { useRoute } from "vue-router";
import myAxios from "../plugins/myAxios";
import imgSrc from "../assets/login.jpg";
const userAccount = ref("");
const userPassword = ref("");
const route = useRoute();
const onSubmit = async () => {
  const res = await myAxios.post("/user/login", {
    userAccount: userAccount.value,
    userPassword: userPassword.value,
  });
  if (res?.code === 0 && res.data) {
    //登陆成功后跳转到之前的页面
    const redirectUrl = (route.query?.redirect as string) ?? "/";
    window.location.href = redirectUrl;
  }
};
</script>

<template>
  <div id="background">
    <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
  <div id="content">
    <h1>Welcome</h1>
    <div id="loginform">
      <h2 style="margin-left: 5%;">Hello ,</h2>
      <h3 style="margin-left: 5%;">Find you want !</h3>
      <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model="userAccount"
          name="userAccount"
          label="账号"
          placeholder="账号"
          :rules="[{ required: true, message: '请填写账号' }]"
        />
        <van-field
          v-model="userPassword"
          type="password"
          name="userPassword"
          label="密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]"
        />
      </van-cell-group>
      <div style="margin: 16px">
        <van-button round block type="default" native-type="submit">
          注册
        </van-button>
      </div>
      <div style="margin: 16px">
        <van-button round block type="primary" native-type="submit">
          登录
        </van-button>
      </div>
      <p style="color: #1989fa; margin-left: 75%;">忘记密码？</p>
    </van-form>
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
