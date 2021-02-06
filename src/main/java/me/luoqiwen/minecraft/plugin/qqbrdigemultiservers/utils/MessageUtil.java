package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.data.FileConfig;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil
{
    private MessageUtil() {}
    public static String ColorFormat(String input)
    {
        return input.replaceAll("&", "§");
    }
    public static List<String> ColorFormat(List<String> input)
    {
        List<String> result = new ArrayList<>();
        input.forEach(s ->
        {
            result.add(ColorFormat(s));
        });
        return result;
    }
    public static String RemoveColor(String input)
    {
        return input.replaceAll("§\\w", "");
    }

    public static String replace(String msg, Replacement... replacements)
    {
        for (Replacement r : replacements)
        {
            msg = msg.replaceAll(r.getOriginal(), r.getChangeTo());
        }
        return msg;
    }

    public static JSONArray buildMessageChain(String... msg)
    {
        JSONArray result = new JSONArray();
        for (String string : msg)
        {
            JSONObject message = new JSONObject();
            message.put("type", "Plain");
            message.put("text", string);
            result.add(message);
        }
        return result;
    }
}
