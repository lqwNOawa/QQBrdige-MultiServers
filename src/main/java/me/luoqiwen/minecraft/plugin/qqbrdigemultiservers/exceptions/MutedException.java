package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

public class MutedException extends MiraiException
{
    public MutedException()
    {
        super(20, "Bot被禁言，Bot当前无法向指定群发送消息");
    }
}
