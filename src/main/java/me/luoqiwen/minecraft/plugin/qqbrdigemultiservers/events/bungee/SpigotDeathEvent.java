package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

import java.net.SocketAddress;

public class SpigotDeathEvent extends Event implements SpigotTransferredEvent
{
    private final ProxyServer proxy = ProxyServer.getInstance();
    private ServerInfo server;
    private final String deathMessage;
    private final ProxiedPlayer player;

    public ServerInfo getServer()
    {
        return server;
    }

    public String getDeathMessage()
    {
        return deathMessage;
    }

    public SpigotDeathEvent(ServerInfo server, ProxiedPlayer player, String deathMessage)
    {
        this.server = server;
        this.deathMessage = deathMessage;
        this.player = player;
    }
    public SpigotDeathEvent(SocketAddress address, String playerName, String deathMessage)
    {
        this.deathMessage = deathMessage;
        player = proxy.getPlayer(playerName);
        proxy.getServers().forEach((s, serverInfo) ->
        {
            if (serverInfo.getSocketAddress().equals(address))
            {
                server = serverInfo;
            }
        });
    }

    public static SpigotDeathEvent parse(JSONObject json, SocketAddress address)
    {
        String type = json.getString("type");
        String subType = json.getString("subType");
        String deathMessage = json.getString("deathMessage");
        String playerName = json.getString("player");
        if (type == null || subType == null || deathMessage == null || playerName == null
                || !type.equals(MiraiMessageEvent.EventType) || !subType.equals("SpigotDeathEvent"))
            return null;
        else
        {
            return new SpigotDeathEvent(address, playerName, deathMessage);
        }
    }
}
