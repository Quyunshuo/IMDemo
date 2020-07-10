package com.quyunshuo.imdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.quyunshuo.imdemo.databinding.ActivityMainBinding
import com.tencent.imsdk.v2.*


class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var mGroupId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        // 群主
        mBinding.lordLoginBtn.setOnClickListener {
            V2TIMManager.getInstance().login(
                "1001",
                GenerateTestUserSig.genTestUserSig("1001"),
                object : V2TIMCallback {
                    override fun onSuccess() {
                        Log.d("qqq", "onSuccess: 1001")
                    }

                    override fun onError(p0: Int, p1: String?) {
                        Log.d("qqq", "onError: 1001")
                    }
                })
        }
        mBinding.createGroupBtn.setOnClickListener { createGroup() }
        mBinding.dissolveGroupBtn.setOnClickListener {
            if (mGroupId.isNotEmpty()) {
                dissolveGroup(mGroupId)
            }
        }
    }

    /**
     * 创建群组
     */
    private fun createGroup() {
        val groupInfo = V2TIMGroupInfo()
        // 组名
        groupInfo.groupName = "testGroup"
        // 组类型 临时会议群（Meeting）
        groupInfo.groupType = "Meeting"
        // 组简介
        groupInfo.introduction = "这是一个测试群组"
        groupInfo.groupID = "100001"
        V2TIMManager.getGroupManager().createGroup(
            groupInfo, ArrayList(), object : V2TIMValueCallback<String?> {
                override fun onError(code: Int, desc: String) {
                    // 创建失败
                    Log.d("qqq", "onError: code:$code msg: $desc")
                }

                override fun onSuccess(groupID: String?) {
                    // 创建成功
                    Log.d("qqq", "onSuccess: groupID:$groupID")
                    groupID?.let { mGroupId = it }
                }
            })
    }

    /**
     * 解散群组
     * @param groupId 群组id
     */
    private fun dissolveGroup(groupId: String) {
        V2TIMManager.getInstance().dismissGroup(groupId, object : V2TIMCallback {
            override fun onSuccess() {
                Log.d("qqq", "onSuccess: 解散成功")
            }

            override fun onError(p0: Int, p1: String?) {
                Log.d("qqq", "onError: 解散失败 code: $p0 msg: $p1")
            }
        })
    }
}