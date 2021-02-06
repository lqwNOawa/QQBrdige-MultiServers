package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events;

import com.alibaba.fastjson.JSONObject;

public interface MiraiMessageEvent
{
    String getMessage();
    String getSenderName();
    long getSenderId();
    JSONObject toJson();
    String EventType = "MiraiMessageEvent";
}