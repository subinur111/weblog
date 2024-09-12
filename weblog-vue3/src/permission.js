// 放置全局路由守卫相关代码

import router from "@/router/index"
import { getToken } from "./composables/auth"
import { showMessage } from "./composables/util"

// 全局前置守卫
// 使用 router.beforeEach 注册
router.beforeEach((to, from, next) => {
    console.log('这是一个全局前置守卫')

    // 当未被授权 且要进入的目标不是Login时，跳转到Login
    // 若用户想访问后台（以 /admin 为前缀的路由）
    let token = getToken()
    if (!token && to.path.startsWith('/admin')) {
        showMessage('请先登录', 'warning')
        next({ path: '/login' })
    }
    else if (token && to.path == '/login') {
        // 若用户已经登录，且重复访问登录页
        showMessage('请勿重复登录', 'warning')
        // 跳转后台首页
        next({ path: '/admin/index' })
    }
    else {
        next()
    }
})

router.afterEach((to, from) => {
    // 动态设置页面 Title
    let title = (to.meta.title ? to.meta.title : '') + ' - Weblog'
    document.title = title
})
