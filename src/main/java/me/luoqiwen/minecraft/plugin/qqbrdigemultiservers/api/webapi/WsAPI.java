package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.webapi;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee.MessageReceivedEvent;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.Replacement;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WsAPI extends WebSocketClient
{
    private final BungeeCordMain plugin;
    private volatile boolean isExceptionClosed = false;
    public WsAPI(BungeeCordMain plugin, String sessionKey)
    {
        super(URI.create("ws://" + plugin.config.getString("URI") + UrlArg.WsMsgChannel(sessionKey)));
        this.plugin = plugin;
    }

    @Override
    public void onOpen(ServerHandshake handshakeData)
    {
        LogUtil.INFO(plugin.config.getString("GameMessage.connected"));
    }

    @Override
    public void onMessage(String message)
    {
        plugin.runAsync(()->
        {
            plugin.getProxy().getPluginManager().callEvent(new MessageReceivedEvent(message));
        });
    }

    @Override
    public void onClose(int code, String reason, boolean remote)
    {
        plugin.runAsync(()->
        {
            LogUtil.Warning(plugin.config.getString("GameMessage.connection_lost",
                    Replacement.create("%Reason%",
                            " |Code:" + code + " |Reason:" + reason + " |Remote:" + remote)));
            if (plugin.isEnabled && !isExceptionClosed && plugin.config.getBoolean("autoReconnect"))
            {
                int interval = plugin.config.getInt("interval");
                LogUtil.INFO(plugin.config.getString("GameMessage.connection_reconnect",
                        Replacement.create("%time%", interval+"")));
                plugin.runAsyncLater(this::connect, interval);
            }
        });
    }

    @Override
    public void onError(Exception ex)
    {
        isExceptionClosed = true;
        LogUtil.Warning(plugin.config.getString("GameMessage.connection_err",
                Replacement.create("%exception%", ex.getLocalizedMessage())));
    }

    public void closeSession()
    {
        isExceptionClosed = true;
        close();
    }
}
