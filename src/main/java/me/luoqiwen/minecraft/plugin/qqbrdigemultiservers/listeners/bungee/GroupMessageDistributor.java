package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.bungee;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.GroupMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class GroupMessageDistributor implements Listener
{
    private final BungeeCordMain plugin;
    public GroupMessageDistributor(BungeeCordMain plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGroupMessage(GroupMessageEvent e)
    {
        plugin.runAsync(()->
        {
            plugin.bungeeChannelAPI.sendPluginMessage(e.toJson());
        });
    }
}