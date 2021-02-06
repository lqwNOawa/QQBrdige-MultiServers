package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.channelapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.SpigotMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.SpigotEventUtil;
import org.bukkit.entity.*;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.Objects;

public class SpigotChannelAPI
{
    private final SpigotMain plugin;
    public SpigotChannelAPI(SpigotMain plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord",
                new SpigotMessageChannelListener(plugin));
    }

    public void sendMessage(String subChannel, String arg)
    {
        plugin.runAsync(()->
        {
            try(ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                DataOutputStream outputStream = new DataOutputStream(byteStream))
            {
                outputStream.writeUTF(subChannel);
                outputStream.writeUTF(arg);
                plugin.getServer().sendPluginMessage(plugin, "BungeeCord", byteStream.toByteArray());
            }
            catch (IOException e)
            {
                e.printStackTrace();
                LogUtil.Exception(e);
            }

        });
    }
    public void sendQQBridgeMessage(String arg)
    {
        sendMessage("QQBridge", arg);
    }

    public static class SpigotMessageChannelListener implements PluginMessageListener
    {
        private final SpigotMain plugin;
        public SpigotMessageChannelListener(SpigotMain plugin)
        {
            this.plugin = plugin;
        }
        @Override
        public void onPluginMessageReceived(String channel, Player player, byte[] message)
        {
            if (!channel.equals("BungeeCord"))
                return;
            plugin.runAsync(()->
            {
                try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(message)))
                {
                    String subChannel = input.readUTF();
                    JSONObject json = JSON.parseObject(input.readUTF());
                    if (subChannel.equals("QQBridge") && json != null && !json.isEmpty())
                    {
                        plugin.getServer().getPluginManager().callEvent(Objects.requireNonNull(SpigotEventUtil.toSpigotEvent(json)));
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    LogUtil.Exception(e);
                }
            });
        }
    }
}
