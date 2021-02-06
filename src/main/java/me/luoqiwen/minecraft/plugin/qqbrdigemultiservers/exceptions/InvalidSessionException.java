package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

public class InvalidSessionException extends MiraiException
{

    public InvalidSessionException()
    {
        super(3, "Session失效或不存在");
    }
}
