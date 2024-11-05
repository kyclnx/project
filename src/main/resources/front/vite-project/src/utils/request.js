import axios from 'axios'

const request = axios.create({
    baseURL: '/api', //所有请求的 URL 前缀，这里是 /api。
    timeout: 60000, //请求的超时时间，这里是 60000 毫秒（即 1 分钟）。
    //自定义请求头部，设置请求体的类型为 JSON。
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
    }
})
//请求拦截器
request.interceptors.request.use(

)
// 响应拦截器，处理的函数有两个参数
request.interceptors.response.use(response =>{
    if (response.data){
        console.log("在request.js中，response中的数据为："+response)
        //如果响应数据存在的话，则返回响应数据
        return response;
    }
    //否则返回整个响应对象
    return response
},(error) =>{
    //失败响应处理函数，直接返回被拒绝的 Promise，传递错误信息。
    return Promise.reject(error)
    }
)
// 导出自定义的请求实例
export default request