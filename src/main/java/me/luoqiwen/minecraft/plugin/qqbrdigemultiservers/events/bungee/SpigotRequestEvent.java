package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.botapi.BotRequestType;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Event;

import java.net.SocketAddress;

public class SpigotRequestEvent extends Event implements SpigotTransferredEvent
{
    private final ProxyServer proxy = ProxyServer.getInstance();
    private final BotRequestType requestType;
    private ServerInfo server;
    private final JSONObject requestJson;

    public BotRequestType getRequestType()
    {
        return requestType;
    }

    public ServerInfo getServer()
    {
        return server;
    }

    public JSONObject getRequestJson()
    {
        return requestJson;
    }

    public SpigotRequestEvent(BotRequestType type, JSONObject requestJson, ServerInfo server)
    {
        requestType = type;
        this.server = server;
        this.requestJson = requestJson;
    }
    public SpigotRequestEvent(BotRequestType type, JSONObject requestJson, SocketAddress address)
    {
        requestType = type;
        this.requestJson = requestJson;
        proxy.getServers().forEach((s, serverInfo) ->
        {
            if (serverInfo.getSocketAddress().equals(address))
                server = serverInfo;
        });
    }

    public static SpigotRequestEvent parse(JSONObject json, SocketAddress address)
    {
        String type = json.getString("type"),
                subType = json.getString("subType");
        BotRequestType requestType = BotRequestType.toType(json.getString("requestType"));
        JSONObject request = json.getJSONObject("request");
        if (type == null || subType == null || requestType == null ||  request == null
                || request.isEmpty() || !type.equals(MiraiMessageEvent.EventType)
                || !subType.equals("SpigotRequestEvent"))
            return null;
        else
        {
            return new SpigotRequestEvent(requestType, request, address);
        }
    }
}