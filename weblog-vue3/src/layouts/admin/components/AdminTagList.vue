<template>
    <div class=" bg-gray-200 h-[50px] flex pr-4 border-b border-slate-200 ">
        <el-tabs v-model="activateTab" type="card" class="demo-tabs" @tab-remove="removeTab" @tab-change="tabChange" style="min-width: 10px;">
            <el-tab-pane v-for="item in tabList" :key="item.path" :label="item.title" :name="item.path" :closable="item.path != '/admin/index' ">
            <!-- {{ item.content }} -->
            </el-tab-pane>
        </el-tabs>

        <!-- 右侧下拉菜单 -->
        <div class="ml-auto mr-4 flex items-center justify-center">
            <el-dropdown >
                <el-icon><Select /></el-icon>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>关闭其他</el-dropdown-item>
                        <el-dropdown-item>关闭全部</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>

</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { Select } from '@element-plus/icons-vue'
import type { TabPaneName } from 'element-plus'
import { onBeforeRouteUpdate, useRoute, useRouter } from 'vue-router';

const route = useRoute()
const router = useRouter()

// 当前被选中的 tab 下标
let tabIndex = 2

// 告诉 el-tab组件 哪个子标签需要呗设置为选中状态 在上面为 v-model= 绑定
const activateTab = ref(route.path)

const tabList = ref([
    {
        'title': '仪表盘',
        'path': "/admin/index",
    },
])

const tabChange = (path) =>{
    console.log("tab-change: path", path)
    router.push(path)
}

// 生命周期钩子 onBeforeRouteUpdate，会在路由切换钱被调用
onBeforeRouteUpdate((to, from) =>{
    activateTab.value = to.path

    let tabTitle = to.meta.title
    let tabPath = to.path
    addTab(tabTitle, tabPath)
})

const addTab = (tabTitle, tabPath) => {
    let isTabExist = tabList.value.findIndex(item => item.path == tabPath) == -1
    if(isTabExist){
        tabList.value.push({
            title: tabTitle,
            path: tabPath
        })
    }
}

const removeTab = (path) => {
    console.log("removeTab-target", path)
    const tabs = tabList.value
    let actTab = activateTab.value
    console.log("removeTab-acttab", actTab)

    // 这里处理下一个标签页是谁的问题
    if ((actTab === path) == true) {
        tabs.forEach((tab, index) => {
            if (tab.path === path) {
                const nextTab = tabs[index + 1] || tabs[index - 1]
                if (nextTab) {
                    actTab = nextTab.path
                    console.log("removeTab-actTab = nextTab.path", actTab)
                }
            }
        })
    }

    // 需要被激活的标签页
    activateTab.value = actTab

    // 过滤掉被删除的标签页, 重新设置回去
    tabList.value = tabList.value.filter((tab) => tab.path != path)

    // 存储到 cookie 中
    // setTa

	// 切换标签页
    console.log("removeTab-activateTab.value", activateTab.value)
    tabChange(activateTab.value)
}

</script>

<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.el-tabs {
    height: 50px;
    --el-tabs-header-height: 50px;
}

.el-tabs__header {
    padding: 0;
    height: 50px;
    margin: 0;
}

.el-tabs__nav-wrap {
    margin-bottom: 0;
}

</style>