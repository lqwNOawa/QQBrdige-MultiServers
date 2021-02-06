package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.data;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.SpigotMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SpigotConfig implements FileConfig
{
    private final SpigotMain plugin;
    private final File ConfigFile;
    private final YamlConfiguration config = new YamlConfiguration();
    public SpigotConfig(SpigotMain plugin)
    {
        this.plugin = plugin;
        ConfigFile = new File(plugin.getDataFolder(), "config.yml");
        try
        {
            load();
        }
        catch (IOException e)
        {
            LogUtil.Exception(e);
        }
    }

    @Override
    public String getString(String path)
    {
        return config.getString(path);
    }

    @Override
    public int getInt(String path)
    {
        return config.getInt(path);
    }

    @Override
    public long getLong(String path)
    {
        return config.getLong(path);
    }

    @Override
    public boolean getBoolean(String path)
    {
        return config.getBoolean(path);
    }

    @Override
    public List<String> getStringList(String path)
    {
        return config.getStringList(path);
    }

    @Override
    public List<Integer> getIntList(String path)
    {
        return config.getIntegerList(path);
    }

    @Override
    public List<Long> getLongList(String path)
    {
        return config.getLongList(path);
    }

    @Override
    public List<Boolean> getBooleanList(String path)
    {
        return config.getBooleanList(path);
    }

    @Override
    public void load() throws IOException
    {
        if (!ConfigFile.exists())
        {
            plugin.saveResource("config-spigot.yml", true);
            File temp = new File(plugin.getDataFolder(), "config-spigot.yml");
            temp.renameTo(ConfigFile);
        }
        try
        {
            config.load(ConfigFile);
        }
        catch (InvalidConfigurationException e)
        {
            LogUtil.Exception(e);
        }
    }

    @Override
    public void loadAsync()
    {
        plugin.runAsync(()->
        {
            try
            {
                load();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void save() throws IOException
    {
        config.save(ConfigFile);
    }

    @Override
    public File getConfigFile()
    {
        return ConfigFile;
    }
}
