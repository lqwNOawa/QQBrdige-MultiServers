package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.spigot;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FriendMessageEvent extends Event implements MiraiMessageEvent
{
    private final String msg;
    private final long senderId;
    private final String senderName;
    private static final HandlerList handlers = new HandlerList();

    public FriendMessageEvent(String msg, long senderId, String senderName)
    {
        super(true);
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

    @Override
    public String getMessage()
    {
        return msg;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static FriendMessageEvent parse(JSONObject json)
    {
        String type = json.getString("type"),
                subType = json.getString("subType"),
                message = json.getString("message"),
                senderName = json.getString("senderName");
        long senderId = json.getLongValue("senderId");
        if (type == null || subType == null || message == null || senderId == 0 || senderName == null
                || !type.equals(EventType) || !subType.equals("FriendMessageEvent"))
        {
            return null;
        }
        else
        {
            return new FriendMessageEvent(message, senderId, senderName);
        }
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
