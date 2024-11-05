<!--<template>-->
<!--  <div class="login-container">-->
<!--    <h1>登录</h1>-->
<!--    <form @submit.prevent="handleLogin">-->
<!--      <div>-->
<!--        <label for="username">用户名</label>-->
<!--        <input-->
<!--            type="text"-->
<!--            id="username"-->
<!--            size="40"-->
<!--            v-model="username"-->
<!--            placeholder="请输入用户名"-->
<!--            required-->
<!--        />-->
<!--      </div>-->
<!--      -->
<!--      <div>-->
<!--        <label for="password">密码</label>-->
<!--        <input-->
<!--            type="password"-->
<!--            id="password"-->
<!--            size="40"-->
<!--            v-model="password"-->
<!--            placeholder="请输入密码"-->
<!--            required-->
<!--        />-->
<!--      </div>-->
<!--      <button type="submit">登录</button>-->
<!--      <p v-if="error" class = 'error'>{{ error }}}</p>-->
<!--    </form>-->
<!--  </div>-->
<!--</template>-->

<!--&lt;!&ndash;、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、&ndash;&gt;-->
<!--<script>-->
<!--export default {-->
<!--  name: "LoginView",-->
<!--  data(){-->
<!--    return {-->
<!--      username: "",-->
<!--      password: "",-->
<!--      error: ""-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    handleLogin() {-->
<!--      //这里进行了密码比对的工作-->
<!--      if (this.password === "123"){-->
<!--        alert("登录成功！ ");-->
<!--        //这里可以添加跳转的逻辑，-->
<!--        this.$router.push("/home");-->
<!--      } else{-->
<!--        this.error = "密码错误，请重试。"-->
<!--      }-->
<!--    },-->
<!--  },-->
<!--};-->
<!--</script>-->



<!--<style scoped>-->
<!--</style>-->
<template>
  <div class="login-container">
    <h1>登录</h1>
    <div class="admin_login_border">
      <div class="admin_input">
        <form @submit.prevent="handleSubmit">
          <ul class="admin_items">
            <li class="input-group">
              <label for="username">用户名：</label>
              <input
                  v-model.trim="form.username"
                  type="text"
                  name="username"
                  id="username"
                  size="40"
                  class="admin_input_style"
                  :class="{ 'input-error': formErrors.username }"
              />
              <span v-if="formErrors.username" class="error-message">
                {{ formErrors.username }}
              </span>
            </li>
            <li class="input-group">
              <label for="password">密码：</label>
              <input
                  v-model.trim="form.password"
                  type="password"
                  name="password"
                  id="password"
                  size="40"
                  class="admin_input_style"
                  :class="{ 'input-error': formErrors.password }"
              />
              <span v-if="formErrors.password" class="error-message">
                {{ formErrors.password }}
              </span>
            </li>
            <li>
              <input
                  type="submit"
                  tabindex="3"
                  value="提交"
                  class="btn btn-primary"
                  :disabled="loading"
              />
            </li>
          </ul>
        </form>
        <div id="message" :class="messageClass">{{ message }}</div>
      </div>
    </div>
    <p class="admin_copyright">
      &copy; 2024 Powered by NJX
    </p>
  </div>
</template>



<!--------------------------------script------------------------------------------->


<script>
import CryptoJS from 'crypto-js'
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import request from "@/utils/request.js";
export default {
  setup() {
    const form = ref({
      username: '',
      password: '',
    });
    const formErrors = ref({});
    const message = ref('');
    const messageClass = ref('');
    const loading = ref(false);

    const router = useRouter();  // 引入 Vue Router

    const validateForm = () => {
      formErrors.value = {};

      if (!form.value.username) {
        formErrors.value.username = '用户名不能为空';
      }
      if (!form.value.password) {
        formErrors.value.password = '密码不能为空';
      }

      return Object.keys(formErrors.value).length === 0;
    };

    const handleSubmit = () => {
      if (!validateForm()) {
        return;
      }

      loading.value = true;
      message.value = '';

      const encPassword = CryptoJS.MD5(form.value.password).toString();

      // 构造加密后的登录表单
      const encLoginForm = {
        username: form.value.username,
        password: encPassword,
      };

      // 向后端发送 POST 请求
      request
          .post('/user/login', encLoginForm)
          .then((response) => {
            console.log("后端返回的详细数据：", response)
            if (response.data.success) {
              message.value = '登录成功！正在跳转...';
              messageClass.value = 'success-message';

              localStorage.setItem("sessionId",response.data.id)
              setTimeout(() => {
                router.push({ path: '/home', query: {} });
              }, 1000);
            } else {
              message.value = '登录失败：' + response.data.message;
              messageClass.value = 'error-message';
            }
          })
          .catch((error) => {
            message.value = '登录失败，请重试';
            messageClass.value = 'error-message';
            console.error('Error:', error);
          })
          .finally(() => {
            loading.value = false;
          });
    };

    return {
      form,
      formErrors,
      message,
      messageClass,
      loading,
      handleSubmit,
    };
  },
};
</script>
<!---------------------------------script结束-------------------------------------->



<style scoped>
body {
  background-image: linear-gradient(to right, #74ebd5, #acb6e5);
  background-size: cover;
  font-family: 'Roboto', sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
}

.admin_login_wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.admin_login_border {
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  width: 100%;
}

h1 {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.admin_input ul {
  list-style: none;
  padding: 0;
}

.admin_items li {
  margin-bottom: 20px;
}

.admin_input_style {
  width: 100%;
  padding: 12px;
  border: 2px solid #ccc;
  border-radius: 6px;
  transition: border-color 0.3s;
  outline: none;
}

.admin_input_style:focus {
  border-color: #3498db;
}

.admin_input_style.input-error {
  border-color: #e74c3c;
}

.btn {
  width: 100%;
  padding: 12px;
  background-color: #3498db;
  border: none;
  border-radius: 6px;
  color: white;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: #2980b9;
}

.btn[disabled] {
  background-color: #bdc3c7;
  cursor: not-allowed;
}

.error-message {
  color: #e74c3c;
  font-size: 14px;
  margin-top: 5px;
}

.success-message {
  color: #2ecc71;
  font-size: 16px;
  margin-top: 20px;
}

#message {
  margin-top: 15px;
  font-size: 14px;
}

.admin_copyright {
  margin-top: 30px;
  font-size: 12px;
  color: #999;
}
</style>
