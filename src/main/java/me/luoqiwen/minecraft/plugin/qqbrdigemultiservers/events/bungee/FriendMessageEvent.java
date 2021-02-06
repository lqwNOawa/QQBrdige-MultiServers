package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import net.md_5.bungee.api.plugin.Event;

public class FriendMessageEvent extends Event implements MiraiMessageEvent
{
    private final String msg;
    private final long senderId;
    private final String senderName;

    public FriendMessageEvent(String msg, long senderId, String senderName)
    {
        this.msg = msg;
        this.senderId = senderId;
        this.senderName = senderName;
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
        result.put("subType", "FriendMessageEvent");
        result.put("message", msg);
        result.put("senderName", senderName);
        result.put("senderId", senderId);
        return result;
    }
}
