package com.wusy.rongyunproject

import android.app.Activity
import com.orhanobut.logger.Logger
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Message
/**
 * 收到消息的处理。
 * @param message 收到的消息实体。
 * @param left 剩余未拉取消息数目。
 * @return
 */
class RongMessageListener: RongIMClient.OnReceiveMessageListener {
    var mActivity:Activity
    constructor(activity: Activity){
        this.mActivity=activity
    }
    override fun onReceived(message: Message?, left: Int): Boolean {
        if(mActivity is MainActivity)
            (mActivity as MainActivity).refreshLogView("\n接收到融云的消息---内容：${message?.content.toString()}")
        return false
    }
}