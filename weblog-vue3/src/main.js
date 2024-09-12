import '@/assets/main.css'

import { createApp } from 'vue';
import App from '@/App.vue';
// 导入路由
import router from '@/router';
// 导入路由守卫
import '@/permission'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'  // icon 图标
// 全局状态管理 pinia
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const app = createApp(App)

const pinia = createPinia()
// 将插件添加到 pinia 实例上
pinia.use(piniaPluginPersistedstate)

// 引入图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(router)
app.use(pinia)
app.mount('#app')
// createApp(App).mount('#app')
