package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.channelapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.BungeeEventUtil;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import java.io.*;
import java.util.Objects;

public class BungeeChannelAPI
{
    private static BungeeCordMain plugin;
    private static final String subChannel = "QQBridge";
    public BungeeChannelAPI(BungeeCordMain plugin)
    {
        BungeeChannelAPI.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, new BungeeMessageChannelListener(plugin));
    }

    public void sendPluginMessage(JSONObject json)
    {
        plugin.runAsync(()->
        {
            try(ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                DataOutputStream outputStream = new DataOutputStream(byteStream))
            {
                outputStream.writeUTF(subChannel);
                outputStream.writeUTF(json.toJSONString());
                plugin.getProxy().getServers().forEach((s, serverInfo) ->
                {
                    serverInfo.sendData("BungeeCord", byteStream.toByteArray());
                });
            }
            catch (EOFException ignored) {}
            catch (IOException e)
            {
                e.printStackTrace();
                LogUtil.Exception(e);
            }
        });
    }

    public void sendPluginMessage(JSONObject json, ServerInfo server)
    {
        plugin.runAsync(()->
        {
            try(ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                DataOutputStream outputStream = new DataOutputStream(byteStream))
            {
                outputStream.writeUTF(subChannel);
                outputStream.writeUTF(json.toJSONString());
                server.sendData("BungeeCord", byteStream.toByteArray());
            }
            catch (EOFException ignored) {}
            catch (IOException e)
            {
                e.printStackTrace();
                LogUtil.Exception(e);
            }
        });
    }

    public static class BungeeMessageChannelListener implements Listener
    {
        private final BungeeCordMain plugin;
        public BungeeMessageChannelListener(BungeeCordMain plugin)
        {
            this.plugin = plugin;
        }
        @EventHandler
        public void onReceive(PluginMessageEvent e)
        {
            plugin.runAsync(()->
            {
                try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData())))
                {
                    String subChannel = in.readUTF();
                    String arg2 = in.readUTF();
                    JSONObject json = JSON.parseObject(arg2);
                    if (!json.isEmpty())
                    {
                        plugin.getProxy().getPluginManager().callEvent(
                                Objects.requireNonNull(BungeeEventUtil.toSpigotTransferredEvent(json, e.getSender().getSocketAddress())));
                    }
                }
                catch (EOFException ignored) {}
                catch (IOException ex)
                {
                    ex.printStackTrace();
                    LogUtil.Exception(plugin.config, ex);
                }
            });
        }
    }
}
