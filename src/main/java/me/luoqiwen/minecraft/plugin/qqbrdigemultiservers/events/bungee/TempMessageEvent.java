package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import net.md_5.bungee.api.plugin.Event;

public class TempMessageEvent extends Event implements MiraiMessageEvent
{
    private final String msg;
    private final String senderName;
    private final long senderId;
    private final long groupId;

    public TempMessageEvent(String msg, long groupId, long senderId, String senderName)
    {
        this.msg = msg;
        this.senderId = senderId;
        this.senderName = senderName;
        this.groupId = groupId;
    }

    @Override
    public String getMessage()
    {
        return msg;
    }

    @Override
    public String getSenderName()
    {
        return senderName;
    }

    @Override
    public long getSenderId()
    {
        return senderId;
    }

    public long getGroupId()
    {
        return groupId;
    }

    @Override
    public JSONObject toJson()
    {
        return new JSONObject()
        {{
            put("type", EventType);
            put("subType", "TempMessageEvent");
            put("message", msg);
            put("senderName", senderName);
            put("senderId", senderId);
            put("groupId", groupId);
        }};
    }
}
