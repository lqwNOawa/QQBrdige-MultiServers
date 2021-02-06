package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

public class MsgTooLangException extends MiraiException
{
    public MsgTooLangException()
    {
        super(30, "消息过长");
    }
}
