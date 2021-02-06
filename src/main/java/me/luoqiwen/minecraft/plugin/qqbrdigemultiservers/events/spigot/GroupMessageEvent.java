package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.spigot;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GroupMessageEvent extends Event implements MiraiMessageEvent
{
    private final String msg;
    private final String groupName;
    private final long groupId;
    private final String senderName;
    private final long senderId;
    private static final HandlerList handlers = new HandlerList();

    public GroupMessageEvent(String msg, String groupName, long groupId, String senderName, long senderId)
    {
        super(true);
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

    public static GroupMessageEvent parse(JSONObject json)
    {
        String type = json.getString("type"),
                subType = json.getString("subType"),
                message = json.getString("message"),
                senderName = json.getString("senderName"),
                groupName = json.getString("groupName");
        long senderId = json.getLongValue("senderId"),
                groupId = json.getLongValue("groupId");
        if (type == null || subType == null || message == null || senderId == 0 || senderName == null
                || groupId == 0 || !type.equals(EventType) || !subType.equals("GroupMessageEvent"))
        {
            return null;
        }
        else
        {
            return new GroupMessageEvent(message, groupName, groupId, senderName, senderId);
        }
    }
}
