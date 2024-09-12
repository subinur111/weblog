// 专门用于放置工具类相关代码
// 这是一个弹出提示框的方法
import 'element-plus/es/components/message/style/css'

import { ElMessage } from "element-plus";

export function showMessage(message = 'show the message', type = 'success', customClass = '') {
    return ElMessage({
        message,
        type,
        customClass,
    })
}