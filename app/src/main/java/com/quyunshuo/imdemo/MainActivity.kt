package com.quyunshuo.imdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.quyunshuo.imdemo.databinding.ActivityMainBinding
import com.tencent.imsdk.v2.V2TIMCallback
import com.tencent.imsdk.v2.V2TIMManager

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        mBinding.loginBtn.setOnClickListener {
            V2TIMManager.getInstance().login(
                "1001",
                GenerateTestUserSig.genTestUserSig("1001"),
                object : V2TIMCallback {
                    override fun onSuccess() {
                        Log.d("qqq", "onSuccess: ")
                    }

                    override fun onError(p0: Int, p1: String?) {
                        Log.d("qqq", "onError: ")
                    }
                })
        }
    }
}