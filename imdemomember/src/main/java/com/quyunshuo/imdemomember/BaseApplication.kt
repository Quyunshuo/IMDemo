package com.quyunshuo.imdemomember
import android.app.Application
import android.util.Log
import com.quyunshuo.imdemomember.GenerateTestUserSig.SDKAPPID
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMSDKConfig
import com.tencent.imsdk.v2.V2TIMSDKListener

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 初始化config对象
        val config = V2TIMSDKConfig()
        // 指定log输出级别
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_DEBUG)
        // 初始化 sdk 并设置 V2TIMSDKListener 的监听对象
        // initSDK 后 SDK 会自动连接网络，网络连接状态可以在 V2TIMSDKListener 回调里面监听
        V2TIMManager.getInstance().initSDK(this, SDKAPPID, config, object : V2TIMSDKListener() {

            // 正在连接到腾讯云服务器
            override fun onConnecting() {
                Log.d("qqq", "onConnecting: 成员")
            }

            // 已经成功连接到腾讯云服务器
            override fun onConnectSuccess() {
                Log.d("qqq", "onConnectSuccess: 成员")
            }

            // 连接腾讯云服务器失败
            override fun onConnectFailed(code: Int, error: String?) {
                Log.d("qqq", "onConnectFailed: 成员")
            }
        })
    }
}