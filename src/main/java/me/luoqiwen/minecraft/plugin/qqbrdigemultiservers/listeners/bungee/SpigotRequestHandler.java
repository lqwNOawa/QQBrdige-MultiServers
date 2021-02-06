package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.bungee;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.SpigotRequestEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SpigotRequestHandler implements Listener
{
    public SpigotRequestHandler(BungeeCordMain plugin)
    {
        this.plugin = plugin;
    }

    private final BungeeCordMain plugin;

    @EventHandler
    public void onRequest(SpigotRequestEvent e)
    {
        plugin.runAsync(()->
        {
            switch (e.getRequestType())
            {
                case GroupMessage: plugin.botAPI.sendGroupMessage(e.getRequestJson());
                case FriendMessage: plugin.botAPI.sendFriendMessage(e.getRequestJson());
                case TempMessage: plugin.botAPI.sendTempMessage(e.getRequestJson());
                case GetSessionKey:
                {
                    JSONObject json = new JSONObject()
                    {{
                        put("type", MiraiMessageEvent.EventType);
                        put("subType", "SessionKeySettingEvent");
                        put("sessionKey", plugin.botAPI.getSessionKey());
                    }};
                    plugin.bungeeChannelAPI.sendPluginMessage(json, e.getServer());
                }
            }
        });
    }
}
