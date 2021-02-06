package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.spigot;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.SpigotMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener
{
    private final SpigotMain plugin;

    public DeathListener(SpigotMain plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent e)
    {
        if (plugin.config.getBoolean("deathMessage"))
        {
            plugin.runAsync(()->
            {
                plugin.messageAPI.sendQQBridgeMessage(e.getDeathMessage());
            });
        }
    }
}
