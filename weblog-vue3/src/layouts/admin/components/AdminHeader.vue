<template>
  <div class="bg-white h-[64px] flex pr-4 border-b border-slate-200">
    <!-- 将左侧导航栏收起的 icon -->
    <div
      class="w-[42px] h-[64px] flex items-center justify-center cursor-pointer text-gray-700 hover:bg-gray-200"
      @click="handleMenuWidth"
    >
      <el-icon>
        <Fold />
      </el-icon>
    </div>

    <!-- 使其靠右  -->
    <div class="ml-auto flex">
      <div
        class="w-[42px] h-[64px] flex items-center justify-center cursor-pointer text-gray-700 hover:bg-gray-200"
      >
        <el-icon>
          <FullScreen />
        </el-icon>
      </div>

      <div class="flex items-center justify-center">
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link flex items-center justify-center">
            <!-- 登录用户的头像 Avatar -->
            <el-avatar :size="25" src="/src/assets/girl.png" />
            {{ userInfoStore.userInfo.data }}
            <el-icon class="el-icon--right">
              <arrow-down />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="updatePassword" >修改密码</el-dropdown-item>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <!-- 弹框 -->
    <el-dialog v-model="dialogFormVisible" title="修改密码" center="true" width="500" >
      <el-form :model="form" center="false">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="form.username" placeholder="请输入用户名" clearable disabled />
        </el-form-item>

        <el-form-item label="新密码" :label-width="formLabelWidth">
          <el-input v-model="form.password" type="password" placeholder="请输入新密码" show-password />
        </el-form-item>

        <el-form-item label="确认新密码" :label-width="formLabelWidth">
          <el-input v-model="form.passwordAdmit" type="password" placeholder="请再次输入新密码" show-password/>
        </el-form-item>         
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="updatePasswordSubmit">确认</el-button>
        </div>
      </template>
      
    </el-dialog>


    <!-- 密码不一致提示 -->
    <el-dialog v-model="centerDialogVisible" title="退出登录" width="500" center>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="logoutSubmit"> 确认 </el-button>
        </div>
      </template>
    </el-dialog>


  </div>
</template>

<script setup>
import { findUserInfo, updatePassword } from "@/api/admin/user";
import { setToken } from "@/composables/auth";
import router from "@/router";
import { useMenuStore } from "@/stores/menu";
import { useUserInfoStore } from "@/stores/userInfo";
import { ref, reactive } from "vue";
import { ElMessage } from 'element-plus'


// 引入 设置菜单宽度的 store
const menuStore = useMenuStore();
// 用户信息 store
const userInfoStore = useUserInfoStore();
// #### 修改密码弹窗 #####
const dialogFormVisible = ref(false);
const formLabelWidth = "140px";
const centerDialogVisible = ref(false)

// 侧边栏 收缩 展开icon 绑定 点击监听事件
const handleMenuWidth = () => {
  menuStore.handleMenuWidth();
  console.log("width, ", menuStore.menuWidth);
};

// 下拉框
const handleCommand = (command) =>{
  console.log("handlecommmand")
  if(command == "updatePassword"){
    dialogFormVisible.value = true
  }
  else if(command == "logout"){
    // tuichudenglu
    centerDialogVisible.value = true
  }
}
const logoutSubmit = () => {
  centerDialogVisible.value = false
  logout()
}
// 退出登录
const logout = () =>{
  // 清除全局变量中用户信息
  userInfoStore.userInfo = {}
  setToken("")
  logoutMessage()
  router.push('/login')
}


// 修改用户名密码，输入框对应表单
const form = reactive({
  // 用户名要写死
  username: userInfoStore.userInfo.data,
  password: "",
  passwordAdmit: "",
});


// 修改密码 点击确认
const updatePasswordSubmit = () => {
  // 点击修改密码，弹出弹框，填写新密码
  if(form.password == form.passwordAdmit){
    // 修改密码
    updatePassword(form.username, form.password).then(res =>{
      if(res.data.success == true){
        updatePasswordSuccMessage()
        logout()
      }
    })
    // 成功后重新登录

  }
  else{
    // message.value =  "两次输入密码不一致，请重新输入"
    passwordAdmitErr()
  }
};


// ######## 消息提示 #######
// 密码不一致提示
const passwordAdmitErr = () => {
    ElMessage.error("两次输入密码不一致，请重新输入")
}

// 修改密码成功提示
const updatePasswordSuccMessage = () => {
  ElMessage({
    message: '修改密码成功，请重新登录',
    type: 'success',
  })
}

// 退出提示
const logoutMessage = () => {
  ElMessage({
    message: '退出成功',
    type: 'success',
  })
}

</script>

<style>
.el-dropdown {
  color: #000;
}
</style>
