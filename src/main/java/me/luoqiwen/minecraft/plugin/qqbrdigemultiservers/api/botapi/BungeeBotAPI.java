package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.botapi;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.webapi.HttpAPI;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.webapi.UrlArg;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.webapi.WsAPI;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions.MiraiException;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.MessageUtil;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.Replacement;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;

/**
 * TODO: 撤回, 事件整合
 */
public class BungeeBotAPI implements Closeable, BotAPIResolver
{
    private final HttpAPI httpAPI;
    private volatile WsAPI wsAPI;
    private final BungeeCordMain plugin;
    private volatile String sessionKey;
    private final HashMap<Integer, Integer> messageIdMap = new HashMap<>();

    public BungeeBotAPI(BungeeCordMain plugin)
    {
        this.plugin = plugin;
        httpAPI = new HttpAPI(plugin);
        try
        {
            sessionKey = initSession();
            wsAPI = new WsAPI(plugin, sessionKey);
            wsAPI.connect();
        }
        catch (IOException | MiraiException e)
        {
            e.printStackTrace();
            LogUtil.Exception(plugin.config, e);
        }
    }

    private String initSession() throws IOException, MiraiException
    {
        if (sessionKey == null)
        {
            //获取Session
            JSONObject session = new JSONObject();
            session.put("authKey", plugin.config.getString("authKey"));
            JSONObject sessionReceived = httpAPI.jsonPost(session, UrlArg.AUTH);
            //检测Mirai异常
            MiraiException.checkException(sessionReceived);

            String sessionKey = sessionReceived.getString("session");
            LogUtil.INFO(plugin.config.getString("GameMessage.sessionKey_got",
                    Replacement.create("%sessionKey%", sessionKey)));

            //验证会话(verify)
            JSONObject verify = new JSONObject();
            verify.put("sessionKey", sessionKey);
            verify.put("qq", plugin.config.getLong("qq"));
            JSONObject verifyReceived = httpAPI.jsonPost(verify, UrlArg.VERIFY);
            //检测Mirai异常
            MiraiException.checkException(verifyReceived);

            LogUtil.INFO(plugin.config.getString("GameMessage.sessionKey_verified"));
            return sessionKey;
        }
        return sessionKey;
    }

    @Override
    public boolean canSend()
    {
        return plugin.isEnabled && wsAPI != null && sessionKey != null;
    }

    @Override
    public int sendGroupMessage(String message, long target)
    {
        if (canSend())
        {
            int id = newIdentify();
            plugin.runAsync(()->
            {
                JSONObject json = new JSONObject();
                json.put("sessionKey", sessionKey);
                json.put("target", target);
                json.put("messageChain", MessageUtil.buildMessageChain(message));

                try
                {
                    JSONObject received = httpAPI.jsonPost(json, UrlArg.SendGroupMsg);
                    MiraiException.checkException(received.getIntValue("code"));
                    synchronized (messageIdMap)
                    {
                        messageIdMap.put(id, received.getIntValue("messageId"));
                    }
                }
                catch (MiraiException | IOException e)
                {
                    e.printStackTrace();
                    LogUtil.Exception(plugin.config, e);
                }
            });
            return id;
        }
        else
            return 0;
    }

    @Override
    public int sendFriendMessage(String message, long targetId)
    {
        if (canSend())
        {
            int id = newIdentify();
            plugin.runAsync(()->
            {
                JSONObject json = new JSONObject();
                json.put("sessionKey", sessionKey);
                json.put("target", targetId);
                json.put("messageChain", MessageUtil.buildMessageChain(message));

                try
                {
                    JSONObject received = httpAPI.jsonPost(json, UrlArg.SendFriendMsg);
                    MiraiException.checkException(received);
                    synchronized (messageIdMap)
                    {
                        messageIdMap.put(id, received.getIntValue("messageId"));
                    }
                }
                catch (MiraiException | IOException e)
                {
                    e.printStackTrace();
                    LogUtil.Exception(plugin.config, e);
                }
            });
            return id;
        }
        else
            return 0;
    }

    @Override
    public int sendTempMessage(String message, long targetId, long targetGroup)
    {
        if (canSend())
        {
            int id = newIdentify();
            plugin.runAsync(()->
            {
                JSONObject json = new JSONObject();
                json.put("sessionKey", sessionKey);
                json.put("qq", targetId);
                json.put("group", targetGroup);
                json.put("messageChain", MessageUtil.buildMessageChain(message));

                try
                {
                    JSONObject received = httpAPI.jsonPost(json, UrlArg.SendTempMsg);
                    MiraiException.checkException(received);
                    synchronized (messageIdMap)
                    {
                        messageIdMap.put(id, received.getIntValue("messageId"));
                    }
                }
                catch (MiraiException | IOException e)
                {
                    e.printStackTrace();
                    LogUtil.Exception(plugin.config, e);
                }
            });
            return id;
        }
        else
            return 0;
    }

    @Override
    public void recall(int id)
    {
        int messageId = -1;
        synchronized (messageIdMap)
        {
            messageId = messageIdMap.getOrDefault(id, -1);
        }
        if (canSend() && messageId != -1)
        {
            int finalMessageId = messageId;
            plugin.runAsync(()->
            {
                JSONObject json = new JSONObject();
                json.put("sessionKey", sessionKey);
                json.put("target", finalMessageId);
                try
                {
                    JSONObject received = httpAPI.jsonPost(json, UrlArg.RECALL);
                    MiraiException.checkException(received);
                }
                catch (MiraiException | IOException e)
                {
                    LogUtil.Exception(plugin.config, e);
                }
            });
        }
    }

    @Override
    public String getSessionKey()
    {
        return sessionKey;
    }

    private int newIdentify()
    {
        synchronized (messageIdMap)
        {
            return messageIdMap.size();
        }
    }

    @Override
    public void close()
    {
        if (wsAPI != null)
            wsAPI.closeSession();
        if (sessionKey != null)
        {
            LogUtil.INFO(plugin.config.getString("GameMessage.sessionKey_release"));
            JSONObject release = new JSONObject();
            release.put("sessionKey", sessionKey);
            release.put("qq", plugin.config.getLong("qq"));
            try
            {
                JSONObject releaseReceived = httpAPI.jsonPost(release, UrlArg.RELEASE);
                MiraiException.checkException(releaseReceived);
                LogUtil.INFO(plugin.config.getString("GameMessage.sessionKey_release_completed"));
            }
            catch (IOException | MiraiException e)
            {
                e.printStackTrace();
                LogUtil.Exception(plugin.config, e);
            }

        }
    }


}
