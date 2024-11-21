<script setup lang="ts">
import axios from 'axios'

//接收父组件传递过来的username
defineProps({
  isOnline: {
    type: Boolean
  },
  chatMes: {
    type: Boolean
  },
  username: {
    type: String,
    default: ''
  },
  toName:{
    type: String,
    default: ''
  }
})
const handleLogout = async ()=>{
  try {
    const response = await axios.post('/api/user/logout')
    if (response.data && response.data.success){
      alert('退出成功')
      window.location.href = '/'
    }else{
      alert('退出失败')
    }
  }catch (error){
    console.error('退出登录时候发生错误', error)
    alert('退出失败')
  }
}

</script>
<template>
  <el-header>
    <!-- 用户名 -->
    <div id="username">
      当前用户 {{ username }}
    </div>

    <!-- 聊天状态 -->
    <div id="chatMes" v-show="chatMes" style="text-align: center; color: #6fbdf3; font-family: 新宋体, serif;">
      正在和 {{ toName }} 聊天
    </div>

    <!-- 在线/离线状态放在最右边 -->
    <div class="status-container">
      <span style="color: green; font-size: 20px;" v-if="isOnline">在线</span>
      <span style="color: red; font-size: 20px;" v-else>离线</span>
    </div>

    <!-- 下拉菜单 -->
    <el-dropdown>
      <span class="el-dropdown-link">
        <el-avatar :size="32" :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
        <el-icon class="el-icon--right">
          <i-ep-arrow-down />
        </el-icon>
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>{{ username }}</el-dropdown-item>
          <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </el-header>
</template>

<style lang="scss" scoped>
.el-header {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 子元素两端对齐 */
  background-color: #dedfe0;

  #username {
    font-size: 16px;
    font-weight: bold;
  }

  #chatMes {
    flex: 1; /* 聊天状态在中间展开 */
    text-align: center;
  }

  .status-container {
    margin-left: auto; /* 将状态推到最右边 */
    padding-right: 15px;
  }

  .el-dropdown {
    .el-dropdown-link {
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}
</style>

