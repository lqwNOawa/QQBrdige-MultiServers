package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.command.spigot;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.SpigotMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpigotCmdHandler implements CommandExecutor
{
    private final SpigotMain plugin;
    public SpigotCmdHandler(SpigotMain plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getName().equals("qq") && sender instanceof Player)
        {
            sender.sendMessage("awa");
            plugin.messageAPI.sendQQBridgeMessage("awa");
            return true;
        }
        return false;
    }
}
