# RongYunProject
> 这是一个Android 接入融云的测试项目，基于融云SDK和WusyLibrary用Kotlin编写。

### 现已实现功能：
1. 音频通话
2. 视频通话
3. 会话消息发送
4. 会话消息接收
5. 消息推送

### 页面效果
![RongYunProject.jpg](https://upload-images.jianshu.io/upload_images/11335240-61aef825d8672f62.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> ### 获取Token
> 融云SDK`connect()`时需要传入Token，而获取Token的API需要服务端去请求，不支持客户端直接获取。在没有搭服务端而客户端需要做测试的时候，可以用融云的API调试功能获取永久Token写死在代码中。

> ### connect时3个回调都不执行
> 方案1：把targetSdkVersion改小于24
方案2：添加 [libsqlite.so](http://rongcloud-web.qiniudn.com/698f304ce3de445d34eb32fe963425ce.gz?attname=libsqlite_3150200.tar.gz) 文件即可，如果直接引入lib中记得在build.gradle的`android{}`下添加代码：
> ```
> android{
> ....
>     sourceSets {
>         main {
>             jni.srcDirs = []
>             jniLibs.srcDirs = ['libs']
>         }
>     }
> }
> ```

> ### 通过`RongCallKit.startSingleCall(this, targetId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO)`拨打音视频的时候，接收端收不到呼叫
> 解决方式是在`RongCallModule`类`onCreate`方法第一行增加代码`mViewLoaded = true`
> 因为融云防止在会话页面覆盖呼叫页面，所以设置了在会话页面加载完毕后再显示呼叫页面。而笔者这里只用了音视频功能，没有会话页面，所以出了这样的问题

> ### 收到被呼叫页面主线程就报错导致闪退
> `Caused by: java.lang.SecurityException: Requires VIBRATE permission`
> 原来是我在是哦用notification的时候用到了震动，但是这个震动也是需要权限的，我们需要在清单文件中配置一把。 
`<uses-permission android:name="android.permission.VIBRATE" />`
这样就搞定了。

> ### 关于CallLib和CallKit
> 针对音视频通话我们引入了两个包，最开始笔者也是傻傻分不清他们之间的关系。接完所有功能后，发现，就像字面的意思，CallKit是融云为我们实现好的音视频业务包，我们只需要按需调用就可以了。而CallKit就是依赖CallLib实现的，有兴趣各位可以通过CallLib自己去扩充我们的音视频业务。
