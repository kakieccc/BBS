<script setup lang="ts">
    import {useRoute} from "vue-router";
    import {ref} from "vue";
    const route = useRoute();
    const editUser = ref({
        editKey: route.query.editKey,
        currentValue: route.query.currentValue,
        editName: route.query.editName,
    })
    const onSubmit = (values: any) => {
    //todo 把editKey currentValue editName提交到后台
    console.log('onSubmit',values);
    }

</script>

<template>
    <van-form @submit="onSubmit">
        <van-field
            v-model="editUser.currentValue"
            :name="editUser.editKey"
            :label="editUser.editName"
        />
        <div style="margin: 16px;">
        <van-button round block type="primary" native-type="submit">
            提交
        </van-button>
        </div>
    </van-form>
</template>

<style>
</style>