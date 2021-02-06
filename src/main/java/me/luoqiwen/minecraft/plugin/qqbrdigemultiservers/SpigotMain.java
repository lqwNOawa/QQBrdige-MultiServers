package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.pluginapi.PlaceHolderAPI;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.channelapi.SpigotChannelAPI;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.command.spigot.SpigotCmdHandler;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.data.SpigotConfig;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.spigot.ChatListener;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.spigot.GroupMessageListener;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TODO: 子服发送事件识别处理
 */
public final class SpigotMain extends JavaPlugin
{
    public volatile SpigotChannelAPI messageAPI;
    public volatile SpigotConfig config;
    @Override
    public void onEnable()
    {
        // Plugin startup logic
        LogUtil.setTarget(getLogger());
        LogUtil.INFO("Enabling QQBridge...");
        LogUtil.INFO("Current Platform: Bukkit, plugin enabled as bukkit bridge mode");
        config = new SpigotConfig(this);
        messageAPI = new SpigotChannelAPI(this);
        getCommand("qq").setExecutor(new SpigotCmdHandler(this));
        PlaceHolderAPI.init(this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new GroupMessageListener(this), this);
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
        LogUtil.INFO("Disabling QQBridge...");
    }

    public void runAsync(Runnable run)
    {
        getServer().getScheduler().runTaskAsynchronously(this, run);
    }
    public void runAsyncLater(Runnable run, long delay)
    {
        getServer().getScheduler().runTaskLaterAsynchronously(this, run, delay);
    }
}
