package com.wusy.rongyunproject

import android.text.method.ScrollingMovementMethod
import com.wusy.wusylibrary.base.BaseActivity
import io.rong.callkit.RongCallKit
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.activity_main.*
import io.rong.message.TextMessage
import io.rong.imlib.IRongCallback
import io.rong.imlib.model.Message


class MainActivity : BaseActivity() {
    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun findView() {
        tv_debug.movementMethod = ScrollingMovementMethod.getInstance()
        btn_audiocall.setOnClickListener {
            var targetId=ed_targetId.text.toString()
            refreshLogView("\n执行单人语音，targetId=$targetId")
            RongCallKit.startSingleCall(this, targetId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO)
        }
        btn_vediocall.setOnClickListener {
            var targetId=ed_targetId.text.toString()
            refreshLogView("\n执行单人视频，targetId=$targetId")
            RongCallKit.startSingleCall(this, targetId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO)
        }
        btn_loginwusy.setOnClickListener {
            RYConnect(Constants.Token_wusy)
        }
        btn_loginrxr.setOnClickListener {
            RYConnect(Constants.Token_rxr)
        }
        btn_exit.setOnClickListener {
            RongIM.getInstance().logout()
            refreshLogView("\n执行退出登录，同时不再接收融云推送")
        }
        btn_sendMessage.setOnClickListener {
            val textMessage = TextMessage.obtain("我是消息内容")
            /**
             * <p>根据会话类型，发送消息。
             * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback} 中的方法回调发送的消息状态及消息体。</p>
             *
             * @param type        会话类型。
             * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、群组 Id 或聊天室 Id。
             * @param content     消息内容，例如 {@link TextMessage}, {@link ImageMessage}。
             * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
             *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
             *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
             * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
             * @param callback    发送消息的回调。参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
             *                    {@link #sendMessage(Message, String, String, IRongCallback.ISendMessageCallback)}
             */
            RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, ed_targetId.text.toString(), textMessage, null, null, object : IRongCallback.ISendMessageCallback {
                override fun onAttached(p0: Message?) {
                }

                override fun onSuccess(p0: Message?) {
                    refreshLogView("\n融云发送消息成功，targetId="+ed_targetId.text.toString()+"消息内容："+textMessage.content)
                }

                override fun onError(p0: Message?, p1: RongIMClient.ErrorCode?) {
                    refreshLogView("\n融云发送消息出现错误")
                }

            })
        }
    }

    override fun init() {
    }

    /**
     * 向TextView中添加内容
     * 当TextView的内容超标时，一直显示最后一行
     */
    fun refreshLogView(msg:String){
        runOnUiThread {
            tv_debug.append(msg)
            var offset=tv_debug.lineCount*tv_debug.lineHeight
            if(offset>tv_debug.height){
                tv_debug.scrollTo(0,offset-tv_debug.height+tv_debug.lineHeight*2)
            }
        }
    }
    private fun RYConnect(token:String){
        refreshLogView("\n开始连接融云，执行connect")
        RongIM.connect(token,object : RongIMClient.ConnectCallback() {
            override fun onSuccess(userId: String?) {
                refreshLogView("\n融云连接成功,userId=$userId\nToken=$token")
                RongIMClient.setOnReceiveMessageListener(RongMessageListener(this@MainActivity))
                refreshLogView("\n融云消息服务注册成功")

            }

            override fun onError(e: RongIMClient.ErrorCode?) {
                refreshLogView("\n融云连接失败")
            }

            override fun onTokenIncorrect() {
                refreshLogView("\n融云连接Token出现错误")
            }
        })
    }
}
