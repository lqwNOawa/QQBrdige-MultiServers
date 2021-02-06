package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

public class InvalidAuthKeyException extends MiraiException
{
    public InvalidAuthKeyException()
    {
        super(1, "错误的auth key");
    }
}
