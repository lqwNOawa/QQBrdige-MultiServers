package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.bungee;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.TempMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TempMessageDistributor implements Listener
{
    private final BungeeCordMain plugin;
    public TempMessageDistributor(BungeeCordMain plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTempMessage(TempMessageEvent e)
    {
        plugin.runAsync(()->
        {
            plugin.bungeeChannelAPI.sendPluginMessage(e.toJson());
        });
    }
}