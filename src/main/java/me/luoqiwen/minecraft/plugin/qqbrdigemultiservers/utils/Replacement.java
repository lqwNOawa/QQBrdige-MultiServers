package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils;

public class Replacement
{
    private final String ori;
    private final String ct;
    public Replacement(String original, String changeTo)
    {
        ori = original;
        ct = changeTo;
    }
    public String getOriginal()
    {
        return ori;
    }
    public String getChangeTo()
    {
        return ct;
    }
    @Override
    public String toString()
    {
        return "{[Replacement] [" + ori + "] to [" + ct + "]}";
    }
    public static Replacement create(String original, String changeTo)
    {
        return new Replacement(original, changeTo);
    }
}