// 声明用户信息的全局状态

import { findUserInfo } from "@/api/admin/user";
import { getToken } from "@/composables/auth";
import { defineStore } from "pinia";
import { ref } from "vue";


export const useUserInfoStore = defineStore('user', () => {
    // 先定义一个变量
    const userInfo = ref('admin')

    // 获取当前用户信息
    function getUserInfo() {
        const token = getToken()
        findUserInfo(token).then(res => {
            console.log("发起请求", res)
            if (res.data) {
                userInfo.value = res.data
                console.log("useroinfo", userInfo.value)
            }
        })
    }

    return { userInfo, getUserInfo }
},
    {
        // pinia 持久化 存储在本地缓存
        persist: true,
    }
)
