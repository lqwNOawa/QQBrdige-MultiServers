package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.spigot;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.SpigotMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.pluginapi.PlaceHolderAPI;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.MessageUtil;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.Replacement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{
    private final SpigotMain plugin;
    public ChatListener(SpigotMain plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e)
    {
        if (plugin.config.getBoolean("QQBridgeEnabled"))
        {
            plugin.runAsync(()->
            {
                String formattedMsg = plugin.config.getString("GameFormat");
                formattedMsg = MessageUtil.replace(formattedMsg,
                        Replacement.create("%message%", e.getMessage()));
                formattedMsg = PlaceHolderAPI.put(formattedMsg, e.getPlayer());
                formattedMsg = MessageUtil.RemoveColor(formattedMsg);
                plugin.messageAPI.sendQQBridgeMessage(ChatMessageBox(formattedMsg, e.getPlayer()).toJSONString());
            });
        }
    }

    private static JSONObject ChatMessageBox(String message, Player player)
    {
        return new JSONObject()
        {{
            put("type", MiraiMessageEvent.EventType);
            put("subType", "SpigotChatEvent");
            put("player", player.getName());
            put("message", message);
        }};
    }
}
