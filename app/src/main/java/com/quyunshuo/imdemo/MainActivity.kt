package com.quyunshuo.imdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quyunshuo.imdemo.databinding.ActivityMainBinding
import com.tencent.imsdk.v2.*


class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mGroupId by lazy { "100001" }

    private val stringBuffer by lazy { StringBuffer() }

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
                        showMsg(" ======>> 登录成功")
                        setSimpleMsgListener()
                        setGroupListener()
                    }

                    override fun onError(p0: Int, p1: String?) {
                        showMsg(" ======>> 登录失败: code: $p0 msg: $p1")
                    }
                })
        }
        mBinding.createGroupBtn.setOnClickListener { createGroup() }
        mBinding.dissolveGroupBtn.setOnClickListener { dissolveGroup(mGroupId) }
        mBinding.sendMsgBtn.setOnClickListener { sendMsg("我tm是群主", mGroupId) }
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
        groupInfo.groupID = mGroupId
        V2TIMManager.getGroupManager().createGroup(
            groupInfo, ArrayList(), object : V2TIMValueCallback<String?> {
                override fun onSuccess(groupID: String?) {
                    showMsg(" ======>> 群创建成功: groupID: $groupID\"")
                }

                override fun onError(code: Int, desc: String) {
                    showMsg(" ======>> 群创建失败: code:$code msg: $desc\"")
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
                showMsg(" ======>> 群解散成功")
            }

            override fun onError(p0: Int, p1: String?) {
                showMsg(" ======>> 群解散失败: code: $p0 msg: $p1")
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