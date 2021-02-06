package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.botapi.BungeeBotAPI;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.channelapi.BungeeChannelAPI;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.data.BungeeConfig;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.bungee.*;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import net.md_5.bungee.api.plugin.Plugin;
import java.util.concurrent.TimeUnit;

/**
 * TODO: Listener(事件分发), 初始化, Bukkit & Bungee 通信, 数据库, 功能(Function)
 */
public final class BungeeCordMain extends Plugin
{
    public volatile BungeeConfig config;
    public volatile boolean isEnabled = false;
    public volatile BungeeBotAPI botAPI;
    public volatile BungeeChannelAPI bungeeChannelAPI;
    @Override
    public void onEnable()
    {
        LogUtil.setTarget(getLogger());
        LogUtil.INFO("Loading QQBridge...");
        isEnabled = true;
        config = new BungeeConfig(this);
        botAPI = new BungeeBotAPI(this);
        bungeeChannelAPI = new BungeeChannelAPI(this);
        getProxy().getPluginManager().registerListener(this, new EventDistributor(this));
        getProxy().getPluginManager().registerListener(this, new FriendMessageDistributor(this));
        getProxy().getPluginManager().registerListener(this, new GroupMessageDistributor(this));
        getProxy().getPluginManager().registerListener(this, new TempMessageDistributor(this));
    }
    @Override
    public void onDisable()
    {
        LogUtil.INFO("Disabling QQBridge...");
        isEnabled = false;
        getProxy().getPluginManager().unregisterListeners(this);
        getProxy().getScheduler().cancel(this);
        getProxy().getPluginManager().unregisterListeners(this);
        botAPI.close();
    }

    public void runAsync(Runnable run)
    {
        getProxy().getScheduler().runAsync(this, run);
    }
    public void runAsyncLater(Runnable run, int delay)
    {
        getProxy().getScheduler().schedule(this, run, delay, TimeUnit.SECONDS);
    }
}
