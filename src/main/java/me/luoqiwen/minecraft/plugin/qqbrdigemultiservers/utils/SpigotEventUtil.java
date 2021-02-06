package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.spigot.FriendMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.spigot.GroupMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.spigot.SessionKeySettingEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.spigot.TempMessageEvent;
import org.bukkit.event.Event;

public class SpigotEventUtil
{
    public synchronized static Event toSpigotEvent(JSONObject json)
    {
        String type = json.getString("type");
        String subType = json.getString("subType");
        if (type == null || subType == null || !type.equals(MiraiMessageEvent.EventType))
            return null;
        else
        {
            switch (subType)
            {
                case "FriendMessageEvent": return FriendMessageEvent.parse(json);
                case "GroupMessageEvent": return GroupMessageEvent.parse(json);
                case "TempMessageEvent": return TempMessageEvent.parse(json);
                case "SessionKeySettingEvent": return SessionKeySettingEvent.parse(json);
                default: return null;
            }
        }
    }
}
