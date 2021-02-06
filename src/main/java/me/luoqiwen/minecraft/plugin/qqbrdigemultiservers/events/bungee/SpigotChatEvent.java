package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

import java.net.SocketAddress;

public class SpigotChatEvent extends Event implements SpigotTransferredEvent
{
    private final ProxyServer proxy = ProxyServer.getInstance();
    private ServerInfo server;
    private final String message;
    private final ProxiedPlayer player;

    public ServerInfo getServer()
    {
        return server;
    }

    public String getMessage()
    {
        return message;
    }

    public ProxiedPlayer getPlayer()
    {
        return player;
    }

    public SpigotChatEvent(ServerInfo server, ProxiedPlayer player, String message)
    {
        this.server = server;
        this.message = message;
        this.player = player;
    }
    public SpigotChatEvent(SocketAddress address, String playerName, String message)
    {
        this.message = message;
        player = proxy.getPlayer(playerName);
        proxy.getServers().forEach((s, serverInfo) ->
        {
            if (serverInfo.getSocketAddress().equals(address))
            {
                server = serverInfo;
            }
        });
    }

    public static SpigotChatEvent parse(JSONObject json, SocketAddress address)
    {
        String type = json.getString("type");
        String subType = json.getString("subType");
        String message = json.getString("message");
        String playerName = json.getString("player");
        if (type == null || subType == null || message == null || playerName == null
                || !type.equals(MiraiMessageEvent.EventType) || !subType.equals("SpigotChatEvent"))
            return null;
        else
        {
            return new SpigotChatEvent(address, playerName, message);
        }
    }
}
