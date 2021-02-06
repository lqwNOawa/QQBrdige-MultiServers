package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.botapi;

import com.alibaba.fastjson.JSONObject;

public interface BotAPIResolver
{
    String getSessionKey();

    /**
     * @return 当前状态是否能发送信息
     */
    boolean canSend();

    /**
     * @param message : 发送的信息
     * @param targetId: 目标群
     * @return 信息识别码，使用 getMessageId(int identify) 获取messageId
     */
    int sendGroupMessage(String message, long targetId);
    default int sendGroupMessage(JSONObject json)
    {
        return sendGroupMessage(json.getString("message"), json.getLongValue("target"));
    }

    /**
     * @param message : 发送的信息
     * @param targetId : Friend的id
     * @return 信息识别码，使用 getMessageId(int identify) 获取messageId
     */
    int sendFriendMessage(String message, long targetId);
    default int sendFriendMessage(JSONObject json)
    {
        return sendFriendMessage(json.getString("message"), json.getLongValue("target"));
    }

    /**
     * @param message : 发送的信息
     * @param targetId : 目标的id
     * @param targetGroup : 目标所在的群
     * @return 信息识别码，使用 recall(int id) 撤回消息
     */
    int sendTempMessage(String message, long targetId, long targetGroup);
    default int sendTempMessage(JSONObject json)
    {
        return sendTempMessage(json.getString("message"), json.getLongValue("target"), json.getLongValue("group"));
    }

    /**
     * @param id : 信息识别码
     */
    void recall(int id);
}
