<template>
    <div>
        <!-- 搜索区 -->
        <el-card class="m-5 flex" shadow="never">
            <div class="flex items-center">
                <el-text>分类名称</el-text>
                <div class="ml-2 mr-3">
                    <el-input v-model="name" style="width: 200px" placeholder="请输入分类名"></el-input>
                </div>

                <el-text>创建日期</el-text>
                <div class="ml-2">
                    <el-date-picker v-model="pickDate" type="daterange" range-separator="至" start-placeholder="开始日期"
                        end-placeholder="结束日期" size="default" @change="datepickerChange"></el-date-picker>
                </div>

                <el-button type="primary" class="ml-3" :icon="Search" @click="searchCategory">查询</el-button>
                <el-button class="ml-3" :icon="RefreshRight">重置</el-button>
            </div>
        </el-card>
    </div>

    <div class="m-5">
        <!-- 新增按钮 -->
        <div class="mb-5">
            <el-button type="primary" @click="addFormVisible = true">
                <el-icon class="mr-1">
                    <Plus />
                </el-icon> 新增 </el-button>
        </div>

        <!-- 新增弹窗展示区 -->
        <el-dialog v-model="addFormVisible" title="新增分类" width="400">
            <el-form :model="addCategoryForm">
                <el-form-item label="类别名" :label-width="100">
                    <el-input v-model="addCategoryForm.name" style="width: 50;" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer flex items-center justify-center">
                    <el-button @click="addFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="addCategorySubmit">
                        确认
                    </el-button>
                </div>
            </template>
        </el-dialog>

        <!-- 列表展示区域 -->
        <el-table :data="tableData" style="width: 100%">
            <el-table-column label="id" width="100">
                <template #default="scope">
                    <div style="
                            display: flex;
                            align-items: center;
                            margin-left: 5px;
                        ">
                        <span>{{ scope.row.id }}</span>
                    </div>
                </template>
            </el-table-column>

            <el-table-column label="分类名称" width="180">
                <template #default="scope">
                    <div style="display: flex; align-items: center">
                        <span>{{ scope.row.name }}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="创建日期" width="350">
                <template #default="scope">
                    <div style="display: flex; align-items: center">
                        <span>{{ scope.row.createtime }}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-popconfirm confirm-button-text="Yes" cancel-button-text="No" :icon="InfoFilled"
                        icon-color="#626AEF" title="Are you sure to delete this?" @confirm="handleDelete(scope.row)">
                        <template #reference>
                            <el-button>Delete</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
    </div>

    <!-- 分页区域 -->
    <div class="mt-10 flex justify-center">
        <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[5, 10, 20]"
            :size="size" :disabled="disabled" :background="background" layout="total, sizes, prev, pager, next, jumper"
            :total="total" @size-change="handleSizeChange" @current-change="searchCategory" />
    </div>

    <!-- 删除 弹窗 -->


</template>

<script lang="ts" setup>
import { Search, RefreshRight } from "@element-plus/icons-vue";
import { reactive, ref } from "vue";
import { Timer, InfoFilled } from "@element-plus/icons-vue";
import type { ComponentSize } from "element-plus";
import { searchCategoryList, addCategory, deleteCategory } from "@/api/admin/category";
import moment from "moment";
import { ElMessage } from 'element-plus'


// 搜索区域参数
const name = ref("");
const pickDate = ref("");
const startDate = ref("");
const endDate = ref("");



interface Category {
    name: string;
    createDate: string;
}

// 分页区域参数
const tableData = ref([]);
const currentPage = ref(1);
const pageSize = ref(5);
const size = ref<ComponentSize>("default");
const background = ref(true);
const disabled = ref(false);
const total = ref(0);

//新增弹窗参数
const addFormVisible = ref(false)

const addCategoryForm = reactive({
    name: ''
})


// 搜索
// 监听日期组件改变事件，并将开始结束时间设置到变量中
const datepickerChange = (e) => {
    startDate.value = moment(e[0]).format("YYYY-MM-DD");
    endDate.value = moment(e[1]).format("YYYY-MM-DD");

    console.log(
        "开始时间：" + startDate.value + ", 结束时间：" + endDate.value
    );
};

const searchCategory = () => {
    console.log("发起搜索请求", name.value, startDate.value);
    // 发起请求
    searchCategoryList(
        currentPage.value,
        pageSize.value,
        name.value,
        startDate.value,
        endDate.value
    ).then((res) => {
        if (res.data.success) {
            tableData.value = res.data.data;
            currentPage.value = res.data.current;
            size.value = res.data.size;
            total.value = res.data.total;
            console.log(tableData.value);
        }

    });
};

const handleSizeChange = (chooseSize) => {
    size.value = chooseSize
    searchCategory()
};


// 新增
//新增弹窗
const addCategorySubmit = () => {
    // 向后端发起请求
    addCategory(addCategoryForm.name).then(res => {
        if (res.data.success) {
            ElMessage({
                message: '添加成功.',
                type: 'success',
            })
        } else (
            ElMessage.error('添加失败.')

        )
        console.log(res.data)
    })
    addFormVisible.value = false;
}

// 删除
const handleDelete = (row: Category) => {

    deleteCategory(row.name).then(res => {
        if (res.data.success) {
            ElMessage({
                message: '删除成功.',
                type: 'success',
            })
        } else {
            ElMessage.error('删除失败.')
        }
    })
    searchCategory();
};


</script>

<style scoped>
.demo-pagination-block+.demo-pagination-block {
    margin-top: 10px;
}

.demo-pagination-block .demonstration {
    margin-bottom: 16px;
}
</style>
