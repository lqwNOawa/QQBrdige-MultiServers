package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.spigot;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TempMessageEvent extends Event implements MiraiMessageEvent
{
    private final String msg;
    private final String senderName;
    private final long senderId;
    private final long groupId;
    private static final HandlerList handlers = new HandlerList();

    public TempMessageEvent(String msg, long groupId , long senderId, String senderName)
    {
        super(true);
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
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
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

    public static TempMessageEvent parse(JSONObject json)
    {
        String type = json.getString("type"),
                subType = json.getString("subType"),
                message = json.getString("message"),
                senderName = json.getString("senderName");
        long senderId = json.getLongValue("senderId"),
                groupId = json.getLongValue("groupId");
        if (type == null || subType == null || message == null || senderId == 0 || senderName == null
                || groupId == 0 || !type.equals(EventType) || !subType.equals("TempMessageEvent"))
        {
            return null;
        }
        else
        {
            return new TempMessageEvent(message, groupId, senderId, senderName);
        }
    }
}
