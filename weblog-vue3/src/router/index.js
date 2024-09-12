import Index from '@/pages/fronted/index.vue'
import Login from '@/pages/admin/login.vue'
import AdminIndex from '@/pages/admin/index.vue'
import Admin from '@/layouts/admin/admin.vue'
import AdminArticleList from '@/pages/admin/article-list.vue'
import AdminCategoryList from '@/pages/admin/category-list.vue'
import AdminTagList from '@/pages/admin/tag-list.vue'
import AdminBlogSetting from '@/pages/admin/blog-setting.vue'
import { createRouter, createWebHashHistory } from 'vue-router'    // 路由器实例是通过调用 createRouter() 函数创建的

// 声明路由 统一管理
// const 声明一个只读常量
const routes = [
    {
        path: '/',         // 路径
        component: Index,          // 对应组建
        meta: {
            title: 'web-log 首页'
        }
    },
    {
        path: '/login',
        component: Login,
        meta: {
            title: 'weblog 登录页',
        }
    },
    {
        path: '/admin',
        component: Admin,
        children: [
            {
                path: '/admin/index',
                component: AdminIndex,
                meta: {
                    title: '仪表盘',
                }
            },
            {
                path: '/admin/article/list',
                component: AdminArticleList,
                meta: {
                    title: '文章管理'
                }
            },
            {
                path: '/admin/category/list',
                component: AdminCategoryList,
                meta: {
                    title: '分类管理'
                }
            },
            {
                path: '/admin/tag/list',
                component: AdminTagList,
                meta: {
                    title: '标签管理'
                }
            },
            {
                path: '/admin/blog/setting',
                component: AdminBlogSetting,
                meta: {
                    title: '博客设置'
                }
            }
        ]
    }

]

// 创建路由： 路由器实例是通过调用 createRouter() 函数创建的
// 选项控制了路由和 URL 路径是如何双向映射的。
const router = createRouter(
    {
        // 指定路由的历史管理方式，hash 模式指的是 URL 的路径是通过 hash 符号（#）进行标识
        history: createWebHashHistory(),
        routes,
    }
)

export default router
