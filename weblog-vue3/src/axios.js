import axios from "axios";
import { getToken } from "./composables/auth";
import { showMessage } from "./composables/util";

// 创建 Axios 实例
const instance = axios.create(
    {
        baseURL: "/api",     // 你的 API 基础 URL
        timeout: 7000,       // 请求超时 时间
    }
)

// 添加请求拦截器
instance.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    const token = getToken()

    // 当 token 不为空时
    if (token) {
        // 添加请求头, key 为 Authorization，value 值的前缀为 'Bearer '
        config.headers['Authorization'] = 'Bearer ' + token
    }
    console.log('请求方法：', config.method);
    console.log('请求URL: ', config.url);
    console.log('请求头：', config.headers);
    console.log('请求数据：', config.data);
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么

    let errorMessage = error.response.data.message || '请求失败'
    showMessage(errorMessage, 'error')

    return Promise.reject(error);
});

// ?
export default instance;