import axios from "@/axios";

// 查询
export function searchCategoryList(currentPage, size, categoryName, startCreateDate, endCreateDate) {
    return axios.post("/admin/category/list", {currentPage, size, categoryName, startCreateDate, endCreateDate})
}

// 新增
export function addCategory(name) {
    return axios.post("/admin/category/add", {name})
}

// 删除
export function deleteCategory(name) {
    return axios.post("/admin/category/delete", {name})
}