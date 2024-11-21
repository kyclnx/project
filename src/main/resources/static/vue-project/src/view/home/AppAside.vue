<script setup lang="ts">
defineProps({
  systemMessages: {
    type: Array,
    default: () => []
  },
  friendsList: {
    type: Array as () => string[],  // 明确声明为字符串数组
    default: () => []
  },
  ShowChat: {
    type: Function,
    required: true,
  }
})
// 声明 `showChat` 事件
const emit = defineEmits<{
  (event: 'showChat', friend: string): void; // 定义事件和传递的参数
}>();

const onFriendClick = (friend: string) => {
  // 发射事件到父组件
  emit('showChat', friend);
};
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <div class="pnl-right-content">
          <div class="tab-btn-content">
            <div class="tab-btn active" id="hot-tab">好友列表</div>
            <div class="pnl-hot">
              <ul class="rel-list unselect">
                <li class="rel-item" v-for="friend in friendsList" :key="friend">
                  <a @click="onFriendClick (friend)">{{ friend }}</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </el-header>

      <el-main>
        <div class="pnl-right-content">
          <div class="pnl-tabs">
            <div class="tab-btn active">系统消息</div>
          </div>
          <div class="pnl-hot">
            <ul class="rel-list unselect" id="broadcastList">
              <li class="rel-item" v-for="(name, index) in systemMessages" :key="index">
                您的好友 {{ name }} 已经上线
              </li>
            </ul>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>


<style scoped lang="scss">
.common-layout {
  height: 100vh; // 确保整个布局占满视口高度
  width: 25vh;
  margin: 0 auto; // 居中显示
}
.el-container {
  height: 100%; // 让容器填充父级
  display: flex;
  flex-direction: column;
}
.el-header{
  flex: 1; // 让 Header 和 Main 各占一半高度
  background: linear-gradient(to right, rgb(247.5, 227.1, 196.5), rgb(247.5, 227.1, 196.5)); // 这里设置为一个橙色到粉色的渐变
}
.el-main {
  background: linear-gradient(to right, rgb(250, 236.4, 216),rgb(250, 236.4, 216)); // 这里设置为一个绿色到蓝色的渐变
}
</style>

