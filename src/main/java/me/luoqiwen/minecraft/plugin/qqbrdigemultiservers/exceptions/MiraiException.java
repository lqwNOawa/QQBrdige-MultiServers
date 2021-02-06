package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class MiraiException extends Exception
{
    private static final HashMap<Integer, Class<? extends MiraiException>> codeMap = new HashMap<Integer, Class<? extends MiraiException>>(10)
    {{
        put(0, null);
        put(1, InvalidAuthKeyException.class);
        put(2, BotNotFoundException.class);
        put(3, InvalidSessionException.class);
        put(4, InactivatedSessionException.class);
        put(5, TargetNotFoundException.class);
        put(10, PermMissedException.class);
        put(20, MutedException.class);
        put(30, MsgTooLangException.class);
        put(400, IllegalAccessException.class);
    }};
    protected final int code;
    private final String reason;

    public MiraiException(int code, String reason)
    {
        this.code = code;
        this.reason = reason;
    }

    public static void checkException(int code) throws MiraiException
    {
        try
        {
            throw codeMap.getOrDefault(code, null).newInstance();
        }
        catch (InstantiationException | java.lang.IllegalAccessException | NullPointerException ignored)
        {
        }
    }
    public static void checkException(JSONObject object) throws MiraiException
    {
        checkException(object.getIntValue("code"));
    }

    @Override
    public String getMessage()
    {
        return reason;
    }

    @Override
    public String toString()
    {
        return "Mirai Exception[code: " + code + ", Reason: " + reason + "]";
    }
}
