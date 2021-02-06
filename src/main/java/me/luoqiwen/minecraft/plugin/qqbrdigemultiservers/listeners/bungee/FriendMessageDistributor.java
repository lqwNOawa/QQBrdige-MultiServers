package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.bungee;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.FriendMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class FriendMessageDistributor implements Listener
{
    private final BungeeCordMain plugin;
    public FriendMessageDistributor(BungeeCordMain plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFriendMessage(FriendMessageEvent e)
    {
        plugin.runAsync(()->
        {
            plugin.bungeeChannelAPI.sendPluginMessage(e.toJson());
        });
    }
}