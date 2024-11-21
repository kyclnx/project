<script setup lang="ts">
import { onMounted, onBeforeUnmount, ref } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'
import AppAside from '@/view/home/AppAside.vue'
import AppHeader from '@/view/home/AppHeader.vue'

// 定义响应式数据
const isOnline = ref(true)
const isShowChat = ref(false)
const chatMes = ref(false)
const username = ref('')
const toName = ref('')
const webSocket = ref<WebSocket | null>(null)
const friendsList = ref<string[]>([])
const systemMessages = ref<string[]>([])
const historyMessage = ref<{ id: string; message: string; fromName: string; time: string }[]>([])
const sendMessage = ref({ toName: '', message: '' })

// 获取用户名并初始化 WebSocket
const init = async () => {
  await fetchUsername()
  createWebSocket()
}

// 获取用户名的函数
const fetchUsername = async () => {
  try {
    const response = await request.get('/user/getUsername')
    username.value = response.data.data
  } catch (error) {
    console.error('获取用户名失败：', error)
  }
}

// 创建 WebSocket 连接
const createWebSocket = () => {
  if (webSocket.value) return
  webSocket.value = new WebSocket('ws://localhost:7024/chat')

  webSocket.value.onopen = () => isOnline.value = true
  webSocket.value.onmessage = handleMessage
  webSocket.value.onclose = () => isOnline.value = false
  webSocket.value.onerror = () => ElMessage.error('WebSocket 连接失败')
}

// 处理服务端消息
const handleMessage = (event: MessageEvent) => {
  const data = JSON.parse(event.data)

  if (data.system) {
    handleSystemMessage(data.message)
  } else {
    handleChatMessage(data)
  }
}

// 处理系统消息
const handleSystemMessage = (names: string[]) => {
  friendsList.value = []
  systemMessages.value = []

  names.forEach(name => {
    if (name !== username.value) {
      friendsList.value.push(name)
      systemMessages.value.push(name)
    }
  })
}

// 处理聊天消息
const handleChatMessage = (messageData: any) => {
  const history = sessionStorage.getItem(messageData.fromName)
  const messageList = history ? JSON.parse(history) : []

  historyMessage.value.push(messageData)
  sessionStorage.setItem(messageData.fromName, JSON.stringify(historyMessage.value))
}

// 切换聊天界面并加载历史消息
const showChat = (name: string) => {
  toName.value = name
  historyMessage.value = loadHistoryMessage(name)
  isShowChat.value = true
  chatMes.value = true
}

// 加载历史消息
const loadHistoryMessage = (name: string) => {
  const history = sessionStorage.getItem(name)
  return history ? JSON.parse(history) : []
}

// 发送消息
const submit = () => {
  const message = sendMessage.value.message.trim()

  if (!message) {
    ElMessage.error('请输入聊天内容')
    return
  }

  sendMessage.value.toName = toName.value
  historyMessage.value.push({ ...sendMessage.value })
  sessionStorage.setItem(toName.value, JSON.stringify(historyMessage.value))

  webSocket.value?.send(JSON.stringify(sendMessage.value))
  sendMessage.value.message = ''
}

// 在组件销毁时关闭 WebSocket
onBeforeUnmount(() => {
  if (webSocket.value) {
    webSocket.value.close()
  }
})

// 在组件挂载时初始化
onMounted(init)
</script>





<template>
  <div class="common-layout">
    <el-container>
      <AppAside
        :systemMessages="systemMessages"
        :friendsList="friendsList"
        @showChat="showChat"
        :chatMes="chatMes"
        :toName="toName"
        :showChat="showChat"
      />
      <el-container class="header-and-main">
        <AppHeader
          :isOnline="isOnline"
          :chatMes="chatMes"
          :username="username"
          :historyMessage="historyMessage"
          :toName="toName"
        />
        <el-main class="chat-container">
          <div id="chatArea" v-show="isShowChat" class="chat-panel">
            <div id="show" class="messages-panel scroll">
              <div class="message" v-for="(message, index) in historyMessage" :key="index">
                <div
                  v-if="message.fromName"
                  class="message-row guest-message"
                >
                  <div class="message-avatar guest-avatar"></div>
                  <div class="message-bubble guest-bubble">
                    <div class="message-name">{{ message.fromName }}</div>
                    <div class="message-text">{{ message.message }}</div>
                  </div>
                </div>
                <div
                  v-else
                  class="message-row host-message"
                >
                  <div class="message-bubble host-bubble">
                    <div class="message-text">{{ message.message }}</div>
                  </div>
                  <div class="message-avatar host-avatar"></div>
                </div>
              </div>
            </div>
            <div class="input-panel">
              <textarea
                class="message-input"
                placeholder="在此输入文字信息..."
                v-model="sendMessage.message"
                @keyup.enter="submit"
              ></textarea>
              <button class="send-button" @click="submit">发送</button>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="scss" scoped>
.common-layout {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.header-and-main {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.el-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #f9f9f9;
  padding: 0;
}

.chat-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
}

.chat-panel {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.messages-panel {
  flex: 1;
  padding: 10px;
  overflow-y: auto; /* 仅聊天区域可以滚动 */
  -webkit-overflow-scrolling: touch; /* 提升滚动性能 */
}

.message-row {
  display: flex;
  align-items: flex-end;
  margin-bottom: 10px;
}

.guest-message {
  justify-content: flex-start;
}

.host-message {
  justify-content: flex-end;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #ddd;
  margin: 0 10px;
}

.message-bubble {
  max-width: 70%;
  padding: 10px 15px;
  border-radius: 15px;
  font-size: 14px;
  position: relative;
}

.guest-bubble {
  background-color: #f1f1f1;
  color: #333;
}

.host-bubble {
  background-color: #66d9ff;
  color: white;
}

.message-text {
  word-break: break-word;
}

.input-panel {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: #f1f1f1;
  border-top: 1px solid #e0e0e0;
}

.message-input {
  flex: 1;
  resize: none;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  font-size: 14px;
}

.send-button {
  margin-left: 10px;
  padding: 10px 20px;
  font-size: 14px;
  border: none;
  border-radius: 5px;
  background-color: #66d9ff;
  color: white;
  cursor: pointer;
}

.send-button:hover {
  background-color: #5cc3e6;
}
</style>


