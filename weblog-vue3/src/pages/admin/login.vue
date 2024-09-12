<template>
    <div class="grid grid-cols-2 h-screen">
        <!-- 默认先适配移动端，占两列，order 用于指定排列顺序，md 用于适配非移动端（PC 端）；背景色为黑色 -->
        <div class="col-span-2 order-2 md:col-span-1 md:order-1 bg-slate-500">
            <div class="flex justify-center items-center h-screen flex-col">
                <h2 class="font-bold text-4xl text-white mb-8">SuSu 博客登录</h2>
                <span class="text-white">一款由 Spring Boot + Mybaits Plus + Vue 3.2 + Vite 4 开发的前后端分离博客。</span>
                <img src="@/assets/developer.png" class="w-1/2">
            </div>
        </div>

        <div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
            <!-- flex-col 用于指定子元素垂直排列 -->
            <div class="flex justify-center items-center h-screen flex-col">
                <h1 class="font-bold mb-5 text-4xl">欢迎回来</h1>
                <div class="flex item-center justify-center text-gray-400 mb-7 space-x-2">
                    <span>---</span>
                    <span>账号密码登录</span>
                    <span>---</span>
                </div>

                <el-form class="w-5/6 md:w-2/5" ref="formRef" :rules="rules" :model="form">
                    <el-form-item prop="username">
                        <!-- 输入框组件 -->
                        <el-input size="large" v-model="form.username" placeholder="请输入用户名" :prefix-icon="User" />
                    </el-form-item>
                    <el-form-item prop="password">
                        <!-- 密码框组件 -->
                        <el-input size="large" type="password" v-model="form.password" placeholder="请输入密码"
                            :prefix-icon="Lock" show-password />
                    </el-form-item>
                    <el-form-item>
                        <!-- 登录按钮，宽度设置为 100% -->
                        <el-button class="w-full mt-2" size="large" type="primary" @click="onSubmit">登录</el-button>
                    </el-form-item>
                </el-form>

            </div>
        </div>
    </div>
</template>

<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { login } from "@/api/admin/user"
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue';
import { useRouter } from 'vue-router'
import { Rule } from 'postcss';
import { ElMessage } from 'element-plus';
import { showMessage } from '@/composables/util';
import { setToken } from '@/composables/auth';
import { useUserInfoStore } from '@/stores/userInfo';



// 定义响应式的表单对象
const form = reactive(
    {
        username: '',
        password: ''
    }
)

const router = useRouter()
const formRef = ref(null)

// 获取用户信息
const userInfoStore = useUserInfoStore()

const rules =  {
    username: [
        {
            required: true,
            message: '姓名不能为空',
            trigger: 'blur',
        },
    ],
    password: [
        {
            required: true,
            message: '密码不能为空',
            trigger: 'blur' ,
        },
    ]
}

// 登录
const onSubmit = () => {
    console.log('登录')

    // 验证表单
    formRef.value.validate((valid) => {
        if (!valid) {
            console.log('表单验证不通过')
            return false
        }

        // 调用 login 接口， 且 打印了 res 对象，用于查看接口能够正常调通。
        login(form.username, form.password).then((res) => {
            console.log(res)
            // 判断是否成功
            if (res.data.success == true) {
                showMessage('登录成功', 'success')

                // 将 token 存储到 cookie 中
                let token = res.data.data.token
                setToken(token)

                // 获取当前用户名到全局变量中
                userInfoStore.getUserInfo()
                console.log("userInfo", userInfoStore.userInfo)

                // 跳转至后台首页
                router.push('/admin/index')
            }
            else {
                let message = res.data.message
                showMessage(message, 'error')
            }
        })
    })
}

// 键盘执行一个回车 后执行这个方法
function onKeyUp(e){
    console.log(e)
    if (e.key == 'Enter') {
        onSubmit()
    }
}

// 这是一个 键盘监听
onMounted(() => {
    console.log('监听键盘')
    document.addEventListener('keyup', onKeyUp)
}
)

// 移除这个键盘监听
onBeforeUnmount(() => {
    document.removeEventListener('keyup', onKeyUp)
})

</script>