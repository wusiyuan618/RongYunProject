package com.wusy.rongyunproject

import android.content.Context


import com.orhanobut.logger.Logger

import io.rong.push.PushType
import io.rong.push.notification.PushMessageReceiver
import io.rong.push.notification.PushNotificationMessage

class SealNotificationReceiver : PushMessageReceiver() {

    override fun onNotificationMessageArrived(context: Context, pushType: PushType, pushNotificationMessage: PushNotificationMessage): Boolean {
        Logger.d("收到融云推送通道的通知---"+pushNotificationMessage.pushTitle)
        return false// 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    override fun onNotificationMessageClicked(context: Context, pushType: PushType, pushNotificationMessage: PushNotificationMessage): Boolean {
        return false// 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
    }
}