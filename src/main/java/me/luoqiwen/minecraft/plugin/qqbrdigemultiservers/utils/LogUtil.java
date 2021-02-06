package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.SpigotMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.data.FileConfig;

import java.util.logging.Logger;

public class LogUtil
{
    private LogUtil() {}
    private static Logger target;
    public static void setTarget(Logger logger)
    {
        target = logger;
    }
    public static void INFO(String... message)
    {
        StringBuilder appended = new StringBuilder();
        for (String s : message)
        {
            appended.append(s);
        }
        target.info("§b" + appended);
    }
    public static void Warning(String... message)
    {
        StringBuilder appended = new StringBuilder();
        for (String s : message)
        {
            appended.append(s);
        }
        target.info("§c" + appended);
    }
    public static void Exception(FileConfig config, Exception e)
    {
        target.warning(config.getString("GameMessage.error",
                Replacement.create("%exception%", e.toString())));
    }
    public static void Exception(Exception e)
    {
        target.warning(e.toString());
    }
    public static void debug(FileConfig config, String... message)
    {
        if (config.getBoolean("debug"))
        {
            StringBuilder appended = new StringBuilder();
            appended.append("[§e[DEBUG]]");
            for (String s : message)
            {
                appended.append(s);
            }
            INFO(appended.toString());
        }
    }
}