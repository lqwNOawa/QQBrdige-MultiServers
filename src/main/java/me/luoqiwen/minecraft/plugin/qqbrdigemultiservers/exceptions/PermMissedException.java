package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.exceptions;

public class PermMissedException extends MiraiException
{
    public PermMissedException()
    {
        super(10, "无操作权限");
    }
}
