<script setup lang="ts">
import { ref } from 'vue';
import request from '@/utils/request';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

// 使用 useRouter 来获取路由对象
const router = useRouter();

// 表单数据和验证规则
const form = ref({
  username: '',
  password: ''
});

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ]
};

// 获取表单实例
const formRef = ref<any>(null);

// 表单提交方法
const onSubmit = () => {
  // 使用表单验证
  formRef.value?.validate((valid: boolean) => {
    if (valid) {
      // 发起登录请求
      request
        .post('/user/login', form.value)
        .then((response) => {
          console.log('登录接口-后台返回的数据：', response)


          // 根据响应数据处理
          if (response.data.success) {
            ElMessage.success('登录成功');
            console.log('跳转到首页面')
            router.push('/home'); // 登录成功后跳转到首页
          } else {
            ElMessage.error(response.data.msg); // 显示错误信息
          }
        })
        .catch((error) => {
          console.log('登录失败-错误信息：', error);
          ElMessage.error('请求失败，请稍后再试');
        });
    } else {
      console.log('表单验证失败');
    }
  });
};
</script>

<template>
  <div class="login">
  <el-form :model="form" :rules="rules" ref="formRef" label-width="120px" label-position="top" size="large">
    <h2>登陆</h2>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" />
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="form.password" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">登录</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>
<style lang="scss" scoped>
.login {
  background-color: #ddd;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;

  .el-form {
    width: 100%;
    max-width: 400px;  /* 适当增加宽度 */
    background-color: #fff;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);  /* 加上阴影效果 */
  }

  .el-form-item {
    margin-top: 20px;
  }

  .el-button {
    width: 100%;
    margin-top: 30px;
  }
}
</style>

