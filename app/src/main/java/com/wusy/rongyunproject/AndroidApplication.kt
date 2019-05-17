package com.wusy.rongyunproject
import com.wusy.wusylibrary.base.BaseApplication
import io.rong.imkit.RongIM


class AndroidApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        RongIM.init(this)
//        val config = PushConfig.Builder()
//                .enableHWPush(true)
//                .enableFCM(true)
//                .build()
//        RongPushClient.setPushConfig(config)
    }
}
