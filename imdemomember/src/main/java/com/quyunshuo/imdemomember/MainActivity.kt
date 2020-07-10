package com.quyunshuo.imdemomember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.quyunshuo.imdemomember.databinding.ActivityMainBinding
import com.tencent.imsdk.v2.*

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val stringBuffer by lazy { StringBuffer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        // 成员
        mBinding.member1LoginBtn.setOnClickListener {
            V2TIMManager.getInstance().login(
                "2001",
                GenerateTestUserSig.genTestUserSig("2001"),
                object : V2TIMCallback {
                    override fun onSuccess() {
                        showMsg(" ======>> 登录成功")
                        setSimpleMsgListener()
                        setGroupListener()
                    }

                    override fun onError(p0: Int, p1: String?) {
                        showMsg(" ======>> 登录失败: code: $p0 msg: $p1")
                    }
                })
        }
        mBinding.joinGroupBtn.setOnClickListener { joinGroup("100001", "测试加群") }
        mBinding.quitGroupBtn.setOnClickListener { quitGroup("100001") }
        mBinding.sendMsgBtn.setOnClickListener { sendMsg("我是群成员1号", "100001") }
    }

    /**
     * 加群
     * @param groupId 群组id
     */
    private fun joinGroup(groupId: String, msg: String) {
        V2TIMManager.getInstance().joinGroup(groupId, msg, object : V2TIMCallback {
            override fun onSuccess() {
                showMsg(" ======>> 加群成功")
            }

            override fun onError(p0: Int, p1: String?) {
                showMsg(" ======>> 加群失败: code: $p0 msg: $p1")
            }
        })
    }

    /**
     * 退群
     */
    private fun quitGroup(groupId: String) {
        V2TIMManager.getInstance().quitGroup(groupId, object : V2TIMCallback {
            override fun onSuccess() {
                showMsg(" ======>> 退群成功")
            }

            override fun onError(p0: Int, p1: String?) {
                showMsg(" ======>> 退群失败: code: $p0 msg: $p1")
            }
        })
    }

    /**
     * 设置简单消息监听
     */
    private fun setSimpleMsgListener() {
        V2TIMManager.getInstance()
            .addSimpleMsgListener(object : V2TIMSimpleMsgListener() {
                override fun onRecvGroupTextMessage(
                    msgID: String?,
                    groupID: String?,
                    sender: V2TIMGroupMemberInfo?,
                    text: String?
                ) {
                    super.onRecvGroupTextMessage(msgID, groupID, sender, text)
                    showMsg(" ======>> 接受到消息: msgID: $msgID groupID: $groupID text: $text")
                }
            })
    }

    /**
     * 设置群组监听
     */
    private fun setGroupListener() {
        V2TIMManager.getInstance().setGroupListener(object : V2TIMGroupListener() {
            override fun onMemberEnter(
                groupID: String?,
                memberList: MutableList<V2TIMGroupMemberInfo>?
            ) {
                super.onMemberEnter(groupID, memberList)
                showMsg(" ======>> 有新成员加入: groupID: $groupID")
            }

            override fun onMemberLeave(
                groupID: String?,
                member: V2TIMGroupMemberInfo?
            ) {
                super.onMemberLeave(groupID, member)
                showMsg(" ======>> 有成员离开群: groupID: $groupID member: ${member.toString()}")
            }
        })
    }

    /**
     * 发送群消息
     */
    private fun sendMsg(msgText: String, groupId: String) {
        V2TIMManager.getInstance()
            .sendGroupTextMessage(msgText, groupId, V2TIMMessage.V2TIM_PRIORITY_HIGH,
                object : V2TIMValueCallback<V2TIMMessage> {
                    override fun onSuccess(p0: V2TIMMessage?) {
                        showMsg(" ======>> 群消息发送成功")
                    }

                    override fun onError(p0: Int, p1: String?) {
                        showMsg(" ======>> 群消息发送失败: code: $p0 msg: $p1")
                    }
                })
    }

    /**
     * 显示信息
     */
    private fun showMsg(msg: String) {
        stringBuffer.append(Utils.getTime() + msg + "\n")
        mBinding.msgTv.text = stringBuffer.toString()
    }
}
