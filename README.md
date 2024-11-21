---

---

此项目为网上聊天地一个项目，我觉得后端设计一般，但是前端，哎呦，浪费了好多天

## 1.1 返回格式设计

如果后端响应成功

- success ：布尔值，表示成功或者失败
- msg：操作结果的消息描述，通常用于提示信息。
- `data`：返回的数据，可以是具体对象、数组或 `null`。

在登陆的时候我希望返回的数据是这样的格式

```json
data: null
msg: "操作成功"
success: true
```

```json
data: null
msg: "用户名或密码错误！！！"
success: false
```

在登陆成功之后我希望返回

```shell
//如果响应成功
{
  "success": true,
  "msg": "操作成功",
  "data": {...}
}
类似于
{
  "success": true,
  "msg": "操作成功",
  "data": {
    "userId": 1,
    "username": "admin"
  }
}
```

```json
//如果响应失败
{
  "success": false,
  "msg": "用户名或密码错误",
  "data": null
}
//类似于
{
  "success": false,
  "msg": "用户名或密码错误",
  "data": null
}
```



### 1.1.1统一的返回结果类R

```java
package org.example.slowly.Common;

/**
 * @author njx
 * @date 2024/11/8
 * @Description 通用返回类
 */
public class R<T> {
    private Boolean success;
    private T data;
    private String msg;

    // 构造函数
    public R(Boolean success, T data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    // 默认成功的静态方法，无数据返回
    public static <T> R<T> succeed() {
        return new R<>(true, null, "操作成功");
    }

    // 成功的静态方法，返回数据
    public static <T> R<T> succeed(T object) {
        return new R<>(true, object, "操作成功");
    }

    // 成功的静态方法，带自定义消息
    public static <T> R<T> succeed(T object, String msg) {
        return new R<>(true, object, msg);
    }

    // 失败的静态方法
    public static <T> R<T> failed(String msg) {
        return new R<>(false, null, msg);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "R{" +
                "success=" + success +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}

```

- 不明白为什么没有显示我的登录的form表单

在App.vue中没有引用并且使用`ElementPlus`，在vite.config.ts中导入并且使用

```vue
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
```

![image-20241108173555502](C:\Users\17083\AppData\Roaming\Typora\typora-user-images\image-20241108173555502.png)

```vue
<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">Aside</el-aside>
      <el-container>
        <el-header>Header</el-header>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>
```

添加一些样式

```shell
npm i sass
```

- 不明白为什么没有显示我的登录的form表单

在App.vue中没有引用并且使用`ElementPlus`，在vite.config.ts中导入并且使用

```vue
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
```