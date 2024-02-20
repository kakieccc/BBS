<script setup lang="ts">
import { BarType } from "../models/bar";
import { barStatusEnum } from "../constants/bar";
import barjpg from "../assets/bar.jpg";
import myAxios from "../plugins/myAxios";
import { Toast } from "vant";
import { onMounted, ref } from "vue";
import { getCurrentUser } from "../services/user";
import { useRouter } from "vue-router";

interface BarCardListProps {
  barList: BarType[];
}

const props = withDefaults(defineProps<BarCardListProps>(), {
  // @ts-ignore
  barList: [] as BarType[],
});

const showPasswordDialog = ref(false);
const password = ref("");
const joinBarId = ref(0);
const currentUser = ref();

const router = useRouter();

onMounted(async () => {
  currentUser.value = await getCurrentUser();
});

const preJoinBar = (bar: BarType) => {
  joinBarId.value = bar.id;
  if (bar.status === 0) {
    doJoinBar();
  } else {
    showPasswordDialog.value = true;
  }
};

const doJoinCancel = () => {
  joinBarId.value = 0;
  password.value = "";
};

/**
 * 加入分区
 */
const doJoinBar = async () => {
  if (!joinBarId.value) {
    return;
  }
  const res = await myAxios.post("/bar/join", {
    barId: joinBarId.value,
    password: password.value,
  });
  if (res?.code === 0) {
    Toast.success("加入成功");
    doJoinCancel();
  } else {
    Toast.fail("加入失败" + (res.description ? `，${res.description}` : ""));
  }
};

/**
 * 跳转至更新分区页
 * @param id
 */
const doUpdateBar = (id: number) => {
  router.push({
    path: "/bar/update",
    query: {
      id,
    },
  });
};

/**
 * 退出分区
 * @param id
 */
const doQuitBar = async (id: number) => {
  const res = await myAxios.post("/bar/quit", {
    barId: id,
  });
  if (res?.code === 0) {
    Toast.success("操作成功");
  } else {
    Toast.fail("操作失败" + (res.description ? `，${res.description}` : ""));
  }
};

/**
 * 解散分区
 * @param id
 */
const doDeleteBar = async (id: number) => {
  const res = await myAxios.post("/bar/delete", {
    id,
  });
  if (res?.code === 0) {
    Toast.success("操作成功");
  } else {
    Toast.fail("操作失败" + (res.description ? `，${res.description}` : ""));
  }
};
</script>

<template>
  <div id="barCardList">
    <van-card
      v-for="bar in props.barList"
      :thumb="barjpg"
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
          v-if="bar.userId !== currentUser?.id && !bar.hasJoin"
          plain
          @click="preJoinBar(bar)"
        >
          加入队伍
        </van-button>
        <van-button
          v-if="bar.userId === currentUser?.id"
          size="small"
          plain
          @click="doUpdateBar(bar.id)"
          >更新分区
        </van-button>
        <!-- 仅加入分区可见 -->
        <van-button
          v-if="bar.userId !== currentUser?.id && bar.hasJoin"
          size="small"
          plain
          @click="doQuitBar(bar.id)"
          >退出分区
        </van-button>
        <van-button
          v-if="bar.userId === currentUser?.id"
          size="small"
          type="danger"
          plain
          @click="doDeleteBar(bar.id)"
          >解散分区
        </van-button>
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
