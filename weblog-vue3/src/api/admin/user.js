// 用于放置用户相关接口
import axios from "@/axios";

// 登录接口
export function login(username, password) {
    return axios.post("/login", { username, password })
}

// 获取用户信息接口
export function findUserInfo(token) {
    return axios.post("/admin/userInfo", { token })
}

// 修改密码接口
export function updatePassword(username, password) {
    return axios.post("/admin/password/update", { username, password })
}