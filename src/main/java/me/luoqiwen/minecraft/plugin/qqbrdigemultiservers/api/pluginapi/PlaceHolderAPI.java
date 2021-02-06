package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.pluginapi;

import me.clip.placeholderapi.PlaceholderAPI;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.SpigotMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class PlaceHolderAPI
{
    private static boolean hasPlaceHolderAPI = false;
    public static void init(SpigotMain plugin)
    {
        if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceHolderAPI"))
            hasPlaceHolderAPI = true;
        else
            LogUtil.Warning("PlaceHolderAPI not found, please don't use placeholders on this server");
    }

    public static String put(String text, Player target)
    {
        if (hasPlaceHolderAPI)
            return PlaceholderAPI.setPlaceholders(target, text);
        else
            return text;
    }

    public static String put(String text, OfflinePlayer target)
    {
        if (hasPlaceHolderAPI)
            return PlaceholderAPI.setPlaceholders(target, text);
        else
            return text;
    }

    public static List<String> put(List<String> text, Player target)
    {
        if (hasPlaceHolderAPI)
            return PlaceholderAPI.setPlaceholders(target, text);
        else
            return text;
    }

    public static List<String> put(List<String> text, OfflinePlayer target)
    {
        if (hasPlaceHolderAPI)
            return PlaceholderAPI.setPlaceholders(target, text);
        else
            return text;
    }
}