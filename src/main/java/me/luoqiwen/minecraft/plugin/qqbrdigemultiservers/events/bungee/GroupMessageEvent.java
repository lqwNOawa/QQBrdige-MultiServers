package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import net.md_5.bungee.api.plugin.Event;

public class GroupMessageEvent extends Event implements MiraiMessageEvent
{
    private final String msg;
    private final String groupName;
    private final long groupId;
    private final String senderName;
    private final long senderId;
    public GroupMessageEvent(String msg, String groupName, long groupId, String senderName, long senderId)
    {
        this.msg = msg;
        this.groupName = groupName;
        this.groupId = groupId;
        this.senderName = senderName;
        this.senderId = senderId;
    }


    public long getGroupId()
    {
        return groupId;
    }

    public String getGroupName()
    {
        return groupName;
    }

    @Override
    public long getSenderId()
    {
        return senderId;
    }

    @Override
    public String getSenderName()
    {
        return senderName;
    }

    @Override
    public String getMessage()
    {
        return msg;
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject result = new JSONObject();
        result.put("type", EventType);
        result.put("subType", "GroupMessageEvent");
        result.put("message", msg);
        result.put("groupName", groupName);
        result.put("groupId", groupId);
        result.put("senderName", senderName);
        result.put("senderId", senderId);
        return result;
    }
}
