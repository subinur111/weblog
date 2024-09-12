<template>
    <div class="bg-slate-800 h-screen" :style="{ width: menuStore.menuWidth }">
        <div class="flex items-center justify-center bg-slate-800 h-[64px] text-white text-2xl">
            Logo
        </div>
    
		<!-- 下方菜单 -->
        <el-menu :default-active="defaultActive" @select="handleSelect" :collapse="isCollapse" :collapse-transition="false">
            <template v-for="(item, index) in menus" :key="index">
                <el-menu-item :index="item.path">
                    <el-icon>
                        <!-- 动态图标 -->
                        <component :is="item.icon"></component>
                    </el-icon>
                    <span>{{ item.name }}</span>
                </el-menu-item>
            </template>
        </el-menu>
    </div>
    
</template>
  
<script setup>
import { useMenuStore } from '@/stores/menu';
import { ref, computed} from 'vue';
import { useRoute, useRouter } from 'vue-router';

// import { Monitor, Document, FolderOpened, PriceTag, Setting} from '@element-plus/icons-vue'
// 定义数组，用于存放菜单需要的数据。
const menus = [
    {
        'name': '仪表盘',
        'icon': 'Monitor',
        'path': '/admin/index'
    },
    {
        'name': '文章管理',
        'icon': 'Document',
        'path': '/admin/article/list',
    },
    {
        'name': '分类管理',
        'icon': 'FolderOpened',
        'path': '/admin/category/list',
    },
    {
        'name': '标签管理',
        'icon': 'PriceTag',
        'path': '/admin/tag/list',
    },
    {
        'name': '博客设置',
        'icon': 'Setting',
        'path': '/admin/blog/setting',
    },
]

const route = useRoute()
const router = useRouter()
const menuStore = useMenuStore()
const isCollapse = computed(() =>  !(menuStore.menuWidth == '250px'))

// 根据路由地址判断哪个菜单被选中
const defaultActive = ref(route.path)

// 路由跳转
const handleSelect = (path) => {
    console.log(path)
    router.push(path)
}

</script>
  
<style>
.el-menu {
    background-color: rgb(30 41 59 / 1);
    border-right: 0;
}

/*
.el-aside {
    width: {{ menuStore.menuWidth}};
}*/

.el-sub-menu__title {
    color: #fff;
}

.el-sub-menu__title:hover {
    background-color: #ffffff10;
}

.el-menu-item.is-active {
    background-color: var(--el-color-primary);
    color: #fff;
}

.el-menu-item.is-active:hover {
    background-color: var(--el-color-primary);
}

.el-menu-item {
    color: #fff;
}

.el-menu-item:hover {
    background-color: #ffffff10;
}

</style>
