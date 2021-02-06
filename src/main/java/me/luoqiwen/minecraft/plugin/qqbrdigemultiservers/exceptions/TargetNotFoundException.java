package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

public class TargetNotFoundException extends MiraiException
{

    public TargetNotFoundException()
    {
        super(5, "发送消息目标不存在(指定对象不存在)");
    }
}
