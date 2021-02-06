package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

public class IllegalAccessException extends MiraiException
{
    public IllegalAccessException()
    {
        super(400, "错误的访问，如参数错误等");
    }
}
