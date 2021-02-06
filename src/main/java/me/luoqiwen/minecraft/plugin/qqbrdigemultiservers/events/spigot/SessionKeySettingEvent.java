package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.spigot;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SessionKeySettingEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private final String sessionKey;
    public SessionKeySettingEvent(String sessionKey)
    {
        super(true);
        this.sessionKey = sessionKey;
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

    public String getSessionKey()
    {
        return sessionKey;
    }

    public static SessionKeySettingEvent parse(JSONObject json)
    {
        String type = json.getString("type"),
                subType = json.getString("subType"),
                sessionKey = json.getString("sessionKey");
        if (type == null || subType == null || sessionKey == null || !type.equals(MiraiMessageEvent.EventType)
            || !subType.equals("SessionKeySettingEvent"))
            return null;
        else
        {
            return new SessionKeySettingEvent(sessionKey);
        }
    }
}
