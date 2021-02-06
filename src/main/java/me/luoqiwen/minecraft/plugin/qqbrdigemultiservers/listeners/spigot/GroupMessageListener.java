package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.spigot;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.SpigotMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.pluginapi.PlaceHolderAPI;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.spigot.GroupMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.MessageUtil;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.Replacement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GroupMessageListener implements Listener
{
    public GroupMessageListener(SpigotMain plugin)
    {
        this.plugin = plugin;
    }

    private final SpigotMain plugin;

    @EventHandler(ignoreCancelled = true)
    public void onGroupMsg(GroupMessageEvent e)
    {
        if (plugin.config.getBoolean("QQBridgeEnabled"))
        {
            plugin.runAsync(()->
            {
                String formatted = plugin.config.getString("GroupFormat");
                formatted = MessageUtil.ColorFormat(formatted);
                formatted = MessageUtil.replace(formatted,
                        Replacement.create("%senderName%", e.getSenderName()),
                        Replacement.create("%senderId%", e.getSenderId()+""),
                        Replacement.create("%groupName%", e.getGroupName()),
                        Replacement.create("%groupId%", e.getGroupId()+""),
                        Replacement.create("%message%", e.getMessage()));
                Player one = plugin.getServer().getOnlinePlayers().stream().iterator().next();
                formatted = one == null ? formatted : PlaceHolderAPI.put(formatted, one);
                plugin.getServer().broadcastMessage(formatted);
            });
        }
    }
}
