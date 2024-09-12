<template>
    <div class="grid h-screen">
        <!-- 外层容器 -->
        <el-container>
        
            <!-- 左边侧边栏 -->
            <el-aside :width='menuStore.menuWidth'>
                <AdminMenu></AdminMenu>
            </el-aside>
            
            <!-- 右边主内容区域 -->
            <el-container>
                <!-- 顶栏容器 -->
                <el-header>
                    <AdminHeader></AdminHeader>
                </el-header>
                
                <el-main>
                    <AdminTagList></AdminTagList>


                    <!-- 下面是主内容 根据 router 变化 动态展示 -->
                    <router-view></router-view>
                    
                </el-main>
                
                <!-- 底栏容器 -->
                <AdminFooter></AdminFooter>
            </el-container>
        </el-container>
    </div>
</template>

  
<script setup>
import AdminHeader from '@/layouts/admin/components/AdminHeader.vue'
import AdminFooter from '@/layouts/admin/components/AdminFooter.vue'
import AdminMenu from '@/layouts/admin/components/AdminMenu.vue'
import AdminTagList from '@/layouts/admin/components/AdminTagList.vue'
import { useMenuStore } from '@/stores/menu';


const menuStore = useMenuStore()
</script>

<style scoped>
.el-header{
    padding: 0;
}

.el-main{
    padding: 0%;
}

</style>