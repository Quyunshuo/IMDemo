package com.quyunshuo.imdemomember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.quyunshuo.imdemomember.databinding.ActivityMainBinding
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
        // 群主
        mBinding.member1LoginBtn.setOnClickListener {
            V2TIMManager.getInstance().login(
                "2001",
                GenerateTestUserSig.genTestUserSig("2001"),
                object : V2TIMCallback {
                    override fun onSuccess() {
                        Log.d("qqq", "onSuccess: 2001")
                    }

                    override fun onError(p0: Int, p1: String?) {
                        Log.d("qqq", "onError: 2001")
                    }
                })
        }
    }
}
