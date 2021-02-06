package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.listeners.bungee;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.FriendMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.GroupMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.MessageReceivedEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.TempMessageEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class EventDistributor implements Listener
{
    private final BungeeCordMain plugin;
    public EventDistributor(BungeeCordMain plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMessageReceived(MessageReceivedEvent e)
    {
        plugin.runAsync(()->
        {
            LogUtil.debug(plugin.config, "MessageReceivedEvent Observed.");
            switch (e.getType())
            {
                case GroupMessage:
                    plugin.getProxy().getPluginManager().callEvent(new GroupMessageEvent(
                            e.getMessageChain().text,
                            e.getSender().groupName,
                            e.getSender().groupId,
                            e.getSender().senderName,
                            e.getSender().senderId
                    ));
                case FriendMessage:
                    plugin.getProxy().getPluginManager().callEvent(new FriendMessageEvent(
                            e.getMessageChain().text,
                            e.getSender().senderId,
                            e.getSender().senderName
                    ));
                case TempMessage:
                    plugin.getProxy().getPluginManager().callEvent(new TempMessageEvent(
                            e.getMessageChain().text,
                            e.getSender().groupId,
                            e.getSender().senderId,
                            e.getSender().senderName
                    ));
            }
        });
    }
}