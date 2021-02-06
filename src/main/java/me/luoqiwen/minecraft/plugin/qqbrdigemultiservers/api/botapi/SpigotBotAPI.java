package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.botapi;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.SpigotMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.MiraiMessageEvent;

import java.util.HashMap;

public class SpigotBotAPI implements BotAPIResolver
{
    private final SpigotMain plugin;
    private final HashMap<Integer, Integer> messageIdMap = new HashMap<>();
    private volatile String sessionKey;
    public SpigotBotAPI(SpigotMain plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public String getSessionKey()
    {
        if (sessionKey != null)
        {
            return sessionKey;
        }
        else
        {
            if (canSend())
            {
                plugin.runAsync(()->
                {
                    JSONObject json = new JSONObject();
                    json.put("type", MiraiMessageEvent.EventType);
                    json.put("subType", "SpigotRequestEvent");
                    json.put("requestType", BotRequestType.GetSessionKey.toString());

                    JSONObject request = new JSONObject();
                    request.put("message", "Requesting sessionKey");

                    json.put("request", request);

                    plugin.messageAPI.sendQQBridgeMessage(json.toJSONString());
                });
            }
            return "Request sent, waiting for setting...";
        }
    }
    public void setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
    }

    @Override
    public boolean canSend()
    {
        return plugin.isEnabled();
    }

    @Override
    public int sendGroupMessage(String message, long targetId)
    {
        int id = newIdentify();
        if (canSend())
        {
            plugin.runAsync(()->
            {
                JSONObject json = new JSONObject();
                json.put("type", MiraiMessageEvent.EventType);
                json.put("subType", "SpigotRequestEvent");
                json.put("requestType", BotRequestType.GroupMessage.toString());

                JSONObject request = new JSONObject();
                request.put("message", message);
                request.put("target", targetId);
                request.put("requestId", id);

                json.put("request", request);

                plugin.messageAPI.sendQQBridgeMessage(json.toJSONString());
            });
        }
        return id;
    }

    @Override
    public int sendFriendMessage(String message, long targetId)
    {
        int id = newIdentify();
        if (canSend())
        {
            plugin.runAsync(()->
            {
                JSONObject json = new JSONObject();
                json.put("type", MiraiMessageEvent.EventType);
                json.put("subType", "SpigotRequestEvent");
                json.put("requestType", BotRequestType.FriendMessage.toString());

                JSONObject request = new JSONObject();
                request.put("message", message);
                request.put("target", targetId);
                request.put("requestId", id);

                json.put("request", request);

                plugin.messageAPI.sendQQBridgeMessage(json.toJSONString());
            });
        }
        return id;
    }

    @Override
    public int sendTempMessage(String message, long targetId, long targetGroup)
    {
        int id = newIdentify();
        if (canSend())
        {
            plugin.runAsync(()->
            {
                JSONObject json = new JSONObject();
                json.put("type", MiraiMessageEvent.EventType);
                json.put("subType", "SpigotRequestEvent");
                json.put("requestType", BotRequestType.TempMessage.toString());

                JSONObject request = new JSONObject();
                request.put("message", message);
                request.put("target", targetId);
                request.put("group", targetGroup);
                request.put("requestId", id);

                json.put("request", request);

                plugin.messageAPI.sendQQBridgeMessage(json.toJSONString());
            });
        }
        return id;
    }

    @Override
    public void recall(int id)
    {

    }

    private int newIdentify()
    {
        synchronized (messageIdMap)
        {
            return messageIdMap.size();
        }
    }
}