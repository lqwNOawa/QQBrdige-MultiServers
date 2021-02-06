package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

public class InactivatedSessionException extends MiraiException
{

    public InactivatedSessionException()
    {
        super(4, "Session未认证(未激活)");
    }
}
