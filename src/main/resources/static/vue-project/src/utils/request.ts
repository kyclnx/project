import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 60000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})

request.interceptors.response.use(response => {
  if (response.data) {
    return response
  }
  return response
}, (error) => {
  return Promise.reject(error)
})
export default request
