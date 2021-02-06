package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

public class BotNotFoundException extends MiraiException
{
    public BotNotFoundException()
    {
        super(2, "指定的Bot不存在");
    }
}
