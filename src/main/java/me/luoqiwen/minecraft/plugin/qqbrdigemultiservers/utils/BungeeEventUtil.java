package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.SpigotChatEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.SpigotDeathEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.SpigotRequestEvent;
import net.md_5.bungee.api.plugin.Event;

import java.net.SocketAddress;

public class BungeeEventUtil
{
    public synchronized static Event toSpigotTransferredEvent(JSONObject json, SocketAddress address)
    {
        String type = json.getString("type");
        String subType = json.getString("subType");
        if (type == null || subType == null || !type.equals(MiraiMessageEvent.EventType))
            return null;
        else
        {
            switch (subType)
            {
                case "SpigotChatEvent": return SpigotChatEvent.parse(json, address);
                case "SpigotDeathEvent": return SpigotDeathEvent.parse(json, address);
                case "SpigotRequestEvent": return SpigotRequestEvent.parse(json, address);
                default: return null;
            }
        }
    }
}
