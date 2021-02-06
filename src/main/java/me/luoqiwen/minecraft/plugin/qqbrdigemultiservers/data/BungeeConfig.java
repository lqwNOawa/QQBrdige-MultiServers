package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.data;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.LogUtil;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.Replacement;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import static me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.MessageUtil.ColorFormat;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: 信息发送器
 *
 */
public class BungeeConfig implements FileConfig
{
    private static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    private volatile BungeeCordMain plugin;
    private volatile File ConfigFile;
    private volatile Configuration config;
    public BungeeConfig(BungeeCordMain plugin)
    {
        ConfigFile = new File(plugin.getDataFolder(), "config.yml");
        this.plugin = plugin;
        try
        {
            load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LogUtil.Warning("Encountered an exception while loading plugin config. Disabling plugin...");
            plugin.getProxy().getScheduler().cancel(plugin);
        }
    }
    @Override
    public void load() throws IOException
    {
        if (!ConfigFile.exists())
        {
            plugin.getDataFolder().mkdirs();
            try
            {
                Files.copy(plugin.getResourceAsStream("config.yml"), ConfigFile.toPath());
            }
            catch (IOException e)
            {
                e.printStackTrace();
                LogUtil.Warning();
            }
        }
        config = provider.load(ConfigFile);
    }
    @Override
    public void loadAsync()
    {
        plugin.getProxy().getScheduler().runAsync(plugin, ()->
        {
            try
            {
                if (!ConfigFile.exists())
                {
                    plugin.getDataFolder().mkdirs();
                    Files.copy(plugin.getResourceAsStream("config.yml"), ConfigFile.toPath());
                }
                config = provider.load(ConfigFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                LogUtil.Warning(plugin.config.getString("Encountered an exception while loading plugin config."));
            }
        });
    }

    @Override
    public String getString(String path)
    {
        return ColorFormat(config.getString(path));
    }
    @Override
    public String getString(String path, Replacement... replacements)
    {
        String get = getString(path);
        for (Replacement r : replacements)
        {
            get = get.replaceAll(r.getOriginal(), r.getChangeTo());
        }
        return get;
    }
    @Override
    public List<String> getStringList(String path)
    {
        return ColorFormat(config.getStringList(path));
    }
    @Override
    public List<String> getStringList(String path, Replacement... replacements)
    {
        List<String> get = getStringList(path);
        List<String> result = new ArrayList<>();
        get.forEach(s ->
        {
            for (Replacement r : replacements)
            {
                s = s.replaceAll(r.getOriginal(), r.getChangeTo());
                result.add(s);
            }
        });
        return result;
    }
    @Override
    public long getLong(String path)
    {
        return config.getLong(path);
    }
    @Override
    public List<Long> getLongList(String path)
    {
        return config.getLongList(path);
    }
    @Override
    public boolean getBoolean(String path)
    {
        return config.getBoolean(path);
    }
    @Override
    public List<Boolean> getBooleanList(String path)
    {
        return config.getBooleanList(path);
    }
    @Override
    public int getInt(String path)
    {
        return config.getInt(path);
    }
    @Override
    public List<Integer> getIntList(String path)
    {
        return config.getIntList(path);
    }

    @Override
    public void save() throws IOException
    {
        provider.save(config, ConfigFile);
    }

    @Override
    public File getConfigFile()
    {
        return ConfigFile;
    }
}